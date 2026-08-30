package com.skjline.fitness.core.security

import com.skjline.fitness.core.security.EncryptedPropertyManager.Companion.DEFAULT_PREFERENCE
import com.skjline.fitness.core.security.EncryptedPropertyManager.Companion.PREF_KEY_AUTH
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.CArrayPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.MemScope
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.value
import platform.CoreFoundation.CFDictionaryCreate
import platform.CoreFoundation.CFDictionaryRef
import platform.CoreFoundation.CFStringRef
import platform.CoreFoundation.CFTypeRef
import platform.CoreFoundation.CFTypeRefVar
import platform.CoreFoundation.kCFAllocatorDefault
import platform.CoreFoundation.kCFBooleanFalse
import platform.CoreFoundation.kCFBooleanTrue
import platform.Foundation.CFBridgingRelease
import platform.Foundation.CFBridgingRetain
import platform.Foundation.NSData
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.Foundation.dataUsingEncoding
import platform.Security.SecCopyErrorMessageString
import platform.Security.SecItemAdd
import platform.Security.SecItemCopyMatching
import platform.Security.SecItemDelete
import platform.Security.SecItemUpdate
import platform.Security.errSecDuplicateItem
import platform.Security.errSecItemNotFound
import platform.Security.kSecAttrAccount
import platform.Security.kSecAttrService
import platform.Security.kSecClass
import platform.Security.kSecClassGenericPassword
import platform.Security.kSecMatchLimit
import platform.Security.kSecMatchLimitOne
import platform.Security.kSecReturnData
import platform.Security.kSecValueData
import platform.darwin.OSStatus
import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.ref.Cleaner
import kotlin.native.ref.createCleaner

/**
 * IOS property manager
 * IOS specific implementation of EncryptedPropertyManager
 *
 * @property service name of the service
 */
@OptIn(ExperimentalForeignApi::class, ExperimentalNativeApi::class)
public class IOSPropertyManager(
    private val service: String = DEFAULT_PREFERENCE,
) : AbstractEncryptedPropertyManager() {
    override fun storeAuthToken(token: String?) {
        setProperty(PREF_KEY_AUTH, token.orEmpty())
    }

    override fun setProperty(
        key: String,
        value: String,
    ) {
        super.setProperty(key, value)
        addOrUpdateKeychainItem(key, value)
    }

    override fun getProperty(key: String): String = getKeychainItem(key).orEmpty()

    private val cleaner: Cleaner?

    private val defaultProperties: Map<CFStringRef?, CFTypeRef?>

    init {
        val cfService: CFTypeRef? = CFBridgingRetain(service)
        defaultProperties =
            mapOf(kSecClass to kSecClassGenericPassword, kSecAttrService to cfService)
        cleaner = createCleaner(cfService) { CFBridgingRelease(it) }
    }

    private fun addOrUpdateKeychainItem(
        key: String,
        value: String?,
    ) {
        value?.let {
            if (!addKeychainItem(key, it)) {
                updateKeychainItem(key, it)
            }
        } ?: run {
            removeKeychainItem(key)
        }
    }

    private fun addKeychainItem(
        key: String,
        value: String,
    ): Boolean =
        cfRetain(
            key,
            value.toNSData(),
        ) { cfKey, cfValue ->
            val status: OSStatus =
                keyChainOperation(
                    kSecAttrAccount to cfKey,
                    kSecValueData to cfValue,
                ) { SecItemAdd(it, null) }
            status.checkError(errSecDuplicateItem)

            status != errSecDuplicateItem
        }

    private fun removeKeychainItem(key: String): Unit =
        cfRetain(key) { cfKey ->
            val status: OSStatus =
                keyChainOperation(
                    kSecAttrAccount to cfKey,
                ) { SecItemDelete(it) }
            status.checkError(errSecItemNotFound)
        }

    private fun updateKeychainItem(
        key: String,
        value: String,
    ): Unit =
        cfRetain(
            key,
            value.toNSData(),
        ) { cfKey, cfValue ->
            val status: OSStatus =
                keyChainOperation(
                    kSecAttrAccount to cfKey,
                    kSecReturnData to kCFBooleanFalse,
                ) {
                    val attributes: CFDictionaryRef? = cfDictionaryOf(kSecValueData to cfValue)
                    val output: OSStatus = SecItemUpdate(it, attributes)
                    CFBridgingRelease(attributes)
                    output
                }
            status.checkError()
        }

    private fun getKeychainItem(key: String): String? =
        cfRetain(key) { cfKey ->
            val cfValue: CFTypeRefVar = alloc<CFTypeRefVar>()
            val status: OSStatus = keyChainOperation(
                kSecAttrAccount to cfKey,
                kSecReturnData to kCFBooleanTrue,
                kSecMatchLimit to kSecMatchLimitOne,
            ) {
                SecItemCopyMatching(it, cfValue.ptr)
            }
            status.checkError(errSecItemNotFound)
            if (status == errSecItemNotFound) {
                return@cfRetain null
            }
            (CFBridgingRelease(cfValue.value) as? NSData).toString()
        }

    private fun hasKeychainItem(key: String): Boolean =
        cfRetain(key) { cfKey ->
            val status: OSStatus =
                keyChainOperation(
                    kSecAttrAccount to cfKey,
                    kSecMatchLimit to kSecMatchLimitOne,
                ) { SecItemCopyMatching(it, null) }

            status != errSecItemNotFound
        }

    private inline fun MemScope.keyChainOperation(
        vararg input: Pair<CFStringRef?, CFTypeRef?>,
        operation: (query: CFDictionaryRef?) -> OSStatus,
    ): OSStatus {
        val query: CFDictionaryRef? = cfDictionaryOf(defaultProperties + mapOf(*input))
        val output: OSStatus = operation(query)
        CFBridgingRelease(query)
        return output
    }

    private fun OSStatus.checkError(vararg expectedErrors: OSStatus) {
        if (this != 0 && this !in expectedErrors) {
            val cfMessage: CFStringRef? = SecCopyErrorMessageString(this, null)
            val nsMessage: NSString? = CFBridgingRelease(cfMessage) as? NSString
            val message: String = nsMessage?.toKString() ?: "Unknown error"
            error("Keychain error $this: $message")
        }
    }
}

@OptIn(ExperimentalForeignApi::class, ExperimentalNativeApi::class)
internal fun MemScope.cfDictionaryOf(vararg items: Pair<CFStringRef?, CFTypeRef?>): CFDictionaryRef? = cfDictionaryOf(mapOf(*items))

@OptIn(ExperimentalForeignApi::class, ExperimentalNativeApi::class)
internal fun MemScope.cfDictionaryOf(map: Map<CFStringRef?, CFTypeRef?>): CFDictionaryRef? {
    val size: Int = map.size
    val keys: CArrayPointer<CFTypeRefVar> = allocArrayOf(*map.keys.toTypedArray())
    val values: CArrayPointer<CFTypeRefVar> = allocArrayOf(*map.values.toTypedArray())
    return CFDictionaryCreate(
        kCFAllocatorDefault,
        keys.reinterpret(),
        values.reinterpret(),
        size.convert(),
        null,
        null,
    )
}

// Turn casts into dot calls for better readability
@Suppress("CAST_NEVER_SUCCEEDS")
internal fun String.toNSString(): NSString = this as NSString

@Suppress("CAST_NEVER_SUCCEEDS")
internal fun NSString.toKString(): String = this as String

@Suppress("CAST_NEVER_SUCCEEDS")
internal fun String.toNSData(): NSData? = toNSString().dataUsingEncoding(NSUTF8StringEncoding)

@OptIn(BetaInteropApi::class)
@Suppress("CAST_NEVER_SUCCEEDS")
internal fun NSData?.toString(): String? =
    this?.let {
        NSString.create(it, NSUTF8StringEncoding)?.toKString()
    }

@OptIn(ExperimentalForeignApi::class, ExperimentalNativeApi::class)
internal inline fun <T> cfRetain(
    value: Any?,
    block: MemScope.(CFTypeRef?) -> T,
): T =
    memScoped {
        val cfValue: CFTypeRef? = CFBridgingRetain(value)
        return try {
            block(cfValue)
        } finally {
            CFBridgingRelease(cfValue)
        }
    }

@OptIn(ExperimentalForeignApi::class, ExperimentalNativeApi::class)
internal inline fun <T> cfRetain(
    value1: Any?,
    value2: Any?,
    block: MemScope.(CFTypeRef?, CFTypeRef?) -> T,
): T =
    memScoped {
        val cfValue1: CFTypeRef? = CFBridgingRetain(value1)
        val cfValue2: CFTypeRef? = CFBridgingRetain(value2)
        return try {
            block(cfValue1, cfValue2)
        } finally {
            CFBridgingRelease(cfValue1)
            CFBridgingRelease(cfValue2)
        }
    }

public actual fun getPropertyManager(): EncryptedPropertyManager = IOSPropertyManager()
