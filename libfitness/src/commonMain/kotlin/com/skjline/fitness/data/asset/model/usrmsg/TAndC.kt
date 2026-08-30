package com.skjline.fitness.data.asset.model.usrmsg

public data class TAndCConfiguration(
    val appName: String,
    val effectiveDate: String,
    val lastUpdated: String,
    val legalContactEmail: String,
    val websiteUrl: String,
    val privacyContactEmail: String
)

public fun String.replaceTAndCPlaceholders(config: TAndCConfiguration): String {
    return this.replace("[App Name]", config.appName)
        .replace("[Effective Date]", config.effectiveDate)
        .replace("[Last Updated]", config.lastUpdated)
        .replace("[Legal Contact Email]", config.legalContactEmail)
        .replace("[Website URL]", config.websiteUrl)
        .replace("[Privacy Contact Email]", config.privacyContactEmail)
}

public enum class TAndC {
    TermsOfUse,
    SafetyDisclaimer,
    PrivacyPolicy
    ;

    fun getDescription(): String {
        return when (this) {
            TermsOfUse -> TERMS_OF_USE
            SafetyDisclaimer -> SAFETY_DISCLAIMER
            PrivacyPolicy -> PRIVACY_POLICY
        }
    }

    companion object {
        const val KEY_EFFECTIVE_DATE = "Effective Date"
        const val KEY_LAST_UPDATED = "Last Updated"
        const val KEY_APP_NAME = "App Name"
        const val KEY_LEGAL_CONTACT = "Legal Contact Email"
        const val KEY_APP_WEBSITE = "Website URL"

        public const val TERMS_OF_USE: String = "# Terms of Use\n" +
                "\n" +
                "**Effective Date:** [Effective Date]\n" +
                "**Last Updated:** [Last Updated]\n" +
                "\n" +
                "These Terms of Use (\"Terms\") govern your access to and use of **[App Name]** (\"App,\" \"we,\" \"us,\" or \"our\"), including the mobile application, associated services, software, content, and features that we provide.\n" +
                "\n" +
                "By downloading, accessing, connecting to a compatible cycling trainer, or otherwise using the App, you agree to these Terms. If you do not agree to these Terms, do not use the App.\n" +
                "\n" +
                "## 1. Description of the App\n" +
                "\n" +
                "[App Name] is a software application for iOS and Android devices that allows users to connect to and control compatible smart cycling trainers and related cycling equipment.\n" +
                "\n" +
                "The App may provide functionality including:\n" +
                "\n" +
                "* Connecting to compatible smart cycling trainers;\n" +
                "* Controlling trainer resistance and power targets;\n" +
                "* ERG and other automatic resistance-control workouts;\n" +
                "* FTP-based training workouts and training targets;\n" +
                "* Recording and displaying cycling performance information;\n" +
                "* Displaying power, cadence, speed, heart rate, or other sensor information when supported;\n" +
                "* Calculating or displaying cycling training metrics; and\n" +
                "* Providing workout, training, and performance information.\n" +
                "\n" +
                "The App is intended for recreational and athletic training purposes. It is not intended to diagnose, treat, cure, prevent, or manage any disease or medical condition.\n" +
                "\n" +
                "## 2. Eligibility\n" +
                "\n" +
                "You must be legally capable of entering into these Terms under the laws applicable to you.\n" +
                "\n" +
                "Unless otherwise expressly stated by us, the App is intended for adults. If you are under the age of 18, do not use the App without appropriate parental or legal-guardian authorization where required by applicable law.\n" +
                "\n" +
                "We may restrict access to the App where required by law or where we reasonably believe use of the App presents a safety, security, or legal risk.\n" +
                "\n" +
                "## 3. User Responsibility\n" +
                "\n" +
                "You are responsible for:\n" +
                "\n" +
                "* Providing accurate information when configuring the App;\n" +
                "* Providing accurate FTP, weight, training, and other user-specific settings;\n" +
                "* Ensuring that your bicycle and trainer are correctly installed and secured;\n" +
                "* Following the manufacturer's instructions for your bicycle and trainer;\n" +
                "* Using the App only for its intended purposes;\n" +
                "* Exercising within your physical capabilities;\n" +
                "* Selecting workout intensities appropriate for your current condition;\n" +
                "* Monitoring your physical condition while exercising; and\n" +
                "* Stopping exercise when necessary.\n" +
                "\n" +
                "You must not use the App in a manner that is unsafe, unlawful, or inconsistent with the manufacturer's instructions for connected equipment.\n" +
                "\n" +
                "## 4. Smart Trainer Control\n" +
                "\n" +
                "The App may send commands to compatible smart cycling trainers.\n" +
                "\n" +
                "Depending on the trainer and workout mode, these commands may cause the trainer to:\n" +
                "\n" +
                "* Increase resistance;\n" +
                "* Decrease resistance;\n" +
                "* Maintain a target resistance;\n" +
                "* Target a particular power output;\n" +
                "* Automatically adjust resistance based on cadence or other trainer information; or\n" +
                "* Perform other functions supported by the connected trainer.\n" +
                "\n" +
                "**Trainer behavior is controlled in part by the connected hardware, firmware, calibration, communication protocol, bicycle configuration, and operating conditions.**\n" +
                "\n" +
                "We do not guarantee that a trainer will always respond to a command exactly as requested or that the trainer will produce a particular physical resistance or power output.\n" +
                "\n" +
                "You remain responsible for understanding how to stop exercising and how to safely disengage or control your trainer using available physical or manufacturer-provided controls.\n" +
                "\n" +
                "## 5. ERG Mode and Automatic Resistance\n" +
                "\n" +
                "Some workouts may use ERG mode or another automatic resistance-control system.\n" +
                "\n" +
                "When such a mode is enabled, the App may automatically adjust trainer resistance without requiring you to manually change the resistance.\n" +
                "\n" +
                "Resistance may increase substantially when your cadence decreases.\n" +
                "\n" +
                "**Do not attempt to maintain a programmed power target if doing so requires an unsafe level of physical effort.**\n" +
                "\n" +
                "You should reduce the workout intensity or stop the workout whenever necessary.\n" +
                "\n" +
                "The App may include software safeguards intended to limit excessive or unexpected trainer commands. These safeguards cannot guarantee that an unsafe condition will never occur.\n" +
                "\n" +
                "## 6. FTP and Training Metrics\n" +
                "\n" +
                "The App may use Functional Threshold Power (\"FTP\") or other performance metrics to calculate workout targets.\n" +
                "\n" +
                "FTP is a cycling performance metric. **FTP is not a medical measurement and does not establish that a particular exercise intensity is medically safe for you.**\n" +
                "\n" +
                "You are responsible for ensuring that your FTP and other training parameters reasonably reflect your current physical condition.\n" +
                "\n" +
                "An inaccurate, outdated, improperly estimated, or otherwise inappropriate FTP may result in workout targets that are unsuitable for you.\n" +
                "\n" +
                "You should not increase exercise intensity solely because the App indicates that a particular target is achievable.\n" +
                "\n" +
                "## 7. Safety\n" +
                "\n" +
                "Your use of the App is subject to the **Safety Disclaimer & Exercise Waiver**, which is incorporated into these Terms by reference.\n" +
                "\n" +
                "You must read and understand the Safety Disclaimer & Exercise Waiver before using the App for exercise.\n" +
                "\n" +
                "The App does not monitor your medical condition and cannot determine whether exercise or a particular workout intensity is safe for you.\n" +
                "\n" +
                "If you experience unusual, severe, or concerning symptoms, stop exercising immediately and seek appropriate medical attention when necessary.\n" +
                "\n" +
                "## 8. Equipment and Compatibility\n" +
                "\n" +
                "The App may support selected smart trainers, power meters, heart-rate monitors, cadence sensors, and other devices.\n" +
                "\n" +
                "Compatibility may depend on:\n" +
                "\n" +
                "* Device model;\n" +
                "* Firmware version;\n" +
                "* Operating-system version;\n" +
                "* Communication protocol;\n" +
                "* Bluetooth or other wireless connectivity;\n" +
                "* Device configuration;\n" +
                "* Calibration;\n" +
                "* Manufacturer changes; and\n" +
                "* Other technical conditions.\n" +
                "\n" +
                "We do not guarantee compatibility with every current or future device.\n" +
                "\n" +
                "We may add, modify, restrict, or discontinue support for particular equipment at any time.\n" +
                "\n" +
                "## 9. Communication Failures\n" +
                "\n" +
                "Wireless communication may be interrupted or degraded by interference, distance, operating-system behavior, hardware limitations, firmware problems, or other conditions.\n" +
                "\n" +
                "If communication with a trainer is interrupted, the trainer may behave according to its own firmware and manufacturer's design.\n" +
                "\n" +
                "**The App should not be considered the sole emergency mechanism for stopping a trainer.**\n" +
                "\n" +
                "You are responsible for understanding and using the trainer manufacturer's emergency and manual-control procedures.\n" +
                "\n" +
                "## 10. Training Information Is Not Medical Advice\n" +
                "\n" +
                "All workout targets, FTP calculations, training zones, performance metrics, recommendations, and other information provided by the App are provided for general training and informational purposes.\n" +
                "\n" +
                "Nothing provided by the App constitutes medical advice, diagnosis, treatment, or a determination of your ability to safely exercise.\n" +
                "\n" +
                "Do not use the App to make medical decisions.\n" +
                "\n" +
                "## 11. User Content and Data\n" +
                "\n" +
                "If the App permits you to create workout data, profiles, settings, comments, or other content, you retain ownership of your content except for the rights necessary for us to operate the App.\n" +
                "\n" +
                "You grant us the limited rights reasonably necessary to store, process, transmit, display, and otherwise provide services involving your content.\n" +
                "\n" +
                "Our handling of personal information is described in our **Privacy Policy**.\n" +
                "\n" +
                "## 12. Software License\n" +
                "\n" +
                "Subject to these Terms, we grant you a limited, non-exclusive, non-transferable, non-sublicensable, revocable license to use the App on devices that you own or control for your personal, non-commercial use.\n" +
                "\n" +
                "You may not:\n" +
                "\n" +
                "* Copy, modify, or distribute the App except as permitted by law;\n" +
                "* Reverse engineer, decompile, or disassemble the App except where applicable law expressly permits it;\n" +
                "* Circumvent security mechanisms;\n" +
                "* Attempt to obtain unauthorized access to the App or its services;\n" +
                "* Use the App to interfere with or damage connected equipment;\n" +
                "* Use automated systems to access the App in an unauthorized manner; or\n" +
                "* Use the App for unlawful purposes.\n" +
                "\n" +
                "## 13. Updates and Changes\n" +
                "\n" +
                "We may modify, update, suspend, or discontinue features of the App at any time.\n" +
                "\n" +
                "Updates may be necessary to address:\n" +
                "\n" +
                "* Safety issues;\n" +
                "* Security vulnerabilities;\n" +
                "* Operating-system changes;\n" +
                "* Trainer firmware changes;\n" +
                "* Compatibility problems;\n" +
                "* Regulatory requirements; or\n" +
                "* Product improvements.\n" +
                "\n" +
                "You acknowledge that certain features may change or become unavailable following an update.\n" +
                "\n" +
                "## 14. Safety-Related Changes\n" +
                "\n" +
                "Because the App can interact with physical exercise equipment, we may temporarily disable or restrict functionality when we reasonably determine that doing so is necessary to address a safety, security, compatibility, or regulatory concern.\n" +
                "\n" +
                "This may include disabling support for a particular trainer model, firmware version, workout type, or resistance-control feature.\n" +
                "\n" +
                "## 15. Intellectual Property\n" +
                "\n" +
                "The App, including its software, design, graphics, interfaces, trademarks, logos, documentation, and other content, is owned by or licensed to [App Name] and is protected by applicable intellectual-property laws.\n" +
                "\n" +
                "Except for the limited license expressly granted in these Terms, no rights are granted to you.\n" +
                "\n" +
                "## 16. Third-Party Services\n" +
                "\n" +
                "The App may interact with third-party products, services, platforms, operating systems, app stores, trainers, sensors, or other technologies.\n" +
                "\n" +
                "Third-party products and services may have their own terms and privacy policies.\n" +
                "\n" +
                "We are not responsible for the availability, accuracy, reliability, security, or performance of third-party products or services.\n" +
                "\n" +
                "## 17. Disclaimer of Warranties\n" +
                "\n" +
                "To the maximum extent permitted by applicable law, the App and its services are provided on an \"AS IS\" and \"AS AVAILABLE\" basis.\n" +
                "\n" +
                "To the maximum extent permitted by law, we disclaim warranties, express or implied, including warranties of merchantability, fitness for a particular purpose, non-infringement, availability, accuracy, reliability, and uninterrupted operation.\n" +
                "\n" +
                "We do not warrant that:\n" +
                "\n" +
                "* The App will operate without interruption;\n" +
                "* The App will be error-free;\n" +
                "* Trainer commands will always be accurate;\n" +
                "* Training information will always be accurate;\n" +
                "* A particular workout result will be achieved;\n" +
                "* The App will be compatible with a particular trainer; or\n" +
                "* The App will meet your individual training or performance goals.\n" +
                "\n" +
                "Nothing in these Terms excludes a warranty or right that cannot legally be excluded.\n" +
                "\n" +
                "## 18. Limitation of Liability\n" +
                "\n" +
                "To the maximum extent permitted by applicable law, [App Name] and its owners, officers, employees, contractors, licensors, and service providers will not be liable for indirect, incidental, consequential, special, exemplary, or punitive damages arising from or related to your use of the App.\n" +
                "\n" +
                "To the maximum extent permitted by applicable law, our aggregate liability arising from your use of the App will be limited to the amount you paid to us for the App or applicable services during the period specified by applicable law, or another limitation required or permitted by applicable law.\n" +
                "\n" +
                "This limitation does not apply to liability that cannot legally be limited or excluded.\n" +
                "\n" +
                "## 19. Indemnification\n" +
                "\n" +
                "To the extent permitted by applicable law, you agree to defend, indemnify, and hold harmless [App Name] and its owners, officers, employees, contractors, and service providers from claims, liabilities, damages, losses, and expenses arising from:\n" +
                "\n" +
                "* Your misuse of the App;\n" +
                "* Your violation of these Terms;\n" +
                "* Your violation of applicable law;\n" +
                "* Your misuse of connected equipment; or\n" +
                "* Your infringement of another person's rights.\n" +
                "\n" +
                "This section does not require you to indemnify us to the extent prohibited by applicable law.\n" +
                "\n" +
                "## 20. Suspension and Termination\n" +
                "\n" +
                "We may suspend or terminate your access to the App if you violate these Terms, misuse the App, create a safety or security risk, or where required by law.\n" +
                "\n" +
                "You may stop using the App at any time.\n" +
                "\n" +
                "Upon termination, provisions that by their nature should survive termination will continue to apply.\n" +
                "\n" +
                "## 21. Governing Law and Dispute Resolution\n" +
                "\n" +
                "**[ATTORNEY TO COMPLETE BASED ON BUSINESS ENTITY AND DISTRIBUTION JURISDICTIONS]**\n" +
                "\n" +
                "These Terms will be governed by the laws of [State/Country], without regard to conflict-of-law principles, except where applicable consumer-protection laws require otherwise.\n" +
                "\n" +
                "Any arbitration, court jurisdiction, class-action waiver, small-claims provision, or other dispute-resolution terms should be reviewed and finalized by qualified legal counsel before publication.\n" +
                "\n" +
                "## 22. Changes to These Terms\n" +
                "\n" +
                "We may update these Terms from time to time.\n" +
                "\n" +
                "When we make material changes, we may provide notice through the App, website, email, or other reasonable means.\n" +
                "\n" +
                "Your continued use of the App after the effective date of updated Terms constitutes acceptance of the updated Terms to the extent permitted by applicable law.\n" +
                "\n" +
                "## 23. Contact\n" +
                "\n" +
                "For questions regarding these Terms, contact:\n" +
                "\n" +
                "**[App Name]**\n" +
                "Email: [Legal Contact Email]\n" +
                "Website: [Website URL]\n" +
                "\n" +
                "## 24. Entire Agreement\n" +
                "\n" +
                "These Terms, together with the Safety Disclaimer & Exercise Waiver and Privacy Policy, constitute the agreement between you and [App Name] regarding your use of the App, except where additional terms expressly apply.\n" +
                "\n" +
                "If any provision is determined to be unenforceable, the remaining provisions will remain in effect to the extent permitted by law.\n" +
                "\n" +
                "---\n" +
                "\n" +
                "**By using [App Name], you acknowledge that you have read and agreed to these Terms of Use.**\n"

        public const val SAFETY_DISCLAIMER = "# Safety Disclaimer & Exercise Waiver\n" +
                "\n" +
                "**Effective Date:** [Effective Date]\n" +
                "**Last Updated:** [Last Updated]\n" +
                "\n" +
                "## IMPORTANT — READ BEFORE EXERCISING\n" +
                "\n" +
                "**[App Name] controls smart cycling trainers and may automatically change trainer resistance during a workout. Cycling and strenuous exercise involve inherent risks, including serious injury, illness, and, in some circumstances, life-threatening medical events.**\n" +
                "\n" +
                "You are responsible for determining whether you are physically capable of safely using the App and performing a particular workout.\n" +
                "\n" +
                "**The App cannot determine whether exercise is medically safe for you.**\n" +
                "\n" +
                "## 1. Not Medical Advice\n" +
                "\n" +
                "[App Name] is a cycling training and smart-trainer control application.\n" +
                "\n" +
                "It is **not a medical device, medical service, healthcare provider, or substitute for professional medical advice, diagnosis, or treatment.**\n" +
                "\n" +
                "The App does not evaluate:\n" +
                "\n" +
                "* Your medical history;\n" +
                "* Your cardiovascular health;\n" +
                "* Your medications;\n" +
                "* Your current physical condition;\n" +
                "* Your exercise tolerance;\n" +
                "* Your risk of injury or illness; or\n" +
                "* Whether a particular exercise intensity is safe for you.\n" +
                "\n" +
                "Training information provided by the App is intended for cycling and fitness purposes only.\n" +
                "\n" +
                "## 2. Exercise Risks\n" +
                "\n" +
                "Physical exercise and cycling involve inherent risks, including:\n" +
                "\n" +
                "* Muscle and joint injuries;\n" +
                "* Overexertion;\n" +
                "* Falls;\n" +
                "* Dehydration;\n" +
                "* Heat-related illness;\n" +
                "* Dizziness;\n" +
                "* Fainting;\n" +
                "* Cardiovascular events;\n" +
                "* Abnormal heart rhythms;\n" +
                "* Loss of coordination;\n" +
                "* Equipment-related injuries; and\n" +
                "* Other serious injury, illness, or medical events.\n" +
                "\n" +
                "You voluntarily assume the ordinary risks associated with physical exercise and use of a cycling trainer, to the extent permitted by applicable law.\n" +
                "\n" +
                "## 3. ERG Mode and Automatic Resistance\n" +
                "\n" +
                "ERG mode and other automatic workout modes may cause your trainer to automatically change resistance.\n" +
                "\n" +
                "**Resistance may increase substantially when your cadence decreases.**\n" +
                "\n" +
                "You should not attempt to maintain a programmed power target if doing so causes excessive or unsafe physical effort.\n" +
                "\n" +
                "If resistance becomes excessive or unexpected:\n" +
                "\n" +
                "1. Reduce or stop pedaling as appropriate;\n" +
                "2. Reduce the workout intensity or exit ERG mode;\n" +
                "3. Use the trainer's manual controls or emergency procedure if necessary; and\n" +
                "4. Stop the workout if you cannot safely continue.\n" +
                "\n" +
                "**Do not rely on the App as the sole means of stopping a trainer in an emergency.**\n" +
                "\n" +
                "The trainer may behave differently depending on its hardware, firmware, calibration, communication state, and manufacturer design.\n" +
                "\n" +
                "## 4. FTP and Workout Targets\n" +
                "\n" +
                "The App may use FTP to calculate workout intensity.\n" +
                "\n" +
                "**FTP is a cycling performance metric. It is not a measure of medical fitness, cardiovascular health, or exercise safety.**\n" +
                "\n" +
                "Your FTP may be inaccurate or outdated.\n" +
                "\n" +
                "Workout targets based on FTP may therefore be inappropriate for your current physical condition.\n" +
                "\n" +
                "Do not attempt to reach or maintain a target merely because the App indicates that you should be capable of doing so.\n" +
                "\n" +
                "**You are always permitted to reduce the intensity or stop the workout.**\n" +
                "\n" +
                "## 5. FTP Tests and Maximal Efforts\n" +
                "\n" +
                "FTP tests, ramp tests, maximal-effort workouts, and other high-intensity workouts may require substantial or near-maximal physical effort.\n" +
                "\n" +
                "Before participating in such workouts, consider whether you are physically prepared for strenuous exercise.\n" +
                "\n" +
                "If you have concerns about your ability to perform high-intensity exercise, consult an appropriate healthcare professional before participating.\n" +
                "\n" +
                "**An FTP test result is a training metric only. It is not a medical assessment.**\n" +
                "\n" +
                "## 6. Stop Exercise When You Feel Abnormal\n" +
                "\n" +
                "**Stop exercising immediately if you experience unusual, severe, or concerning symptoms.**\n" +
                "\n" +
                "Examples include:\n" +
                "\n" +
                "* Chest pain, pressure, or discomfort;\n" +
                "* Severe or unusual shortness of breath;\n" +
                "* Fainting or feeling that you may faint;\n" +
                "* Severe dizziness;\n" +
                "* Confusion;\n" +
                "* Unusual or irregular heartbeat or palpitations;\n" +
                "* Sudden weakness or loss of coordination;\n" +
                "* Severe or unexpected pain; or\n" +
                "* Any other symptom that feels abnormal or concerning.\n" +
                "\n" +
                "If symptoms are severe, persistent, or potentially life-threatening, **seek appropriate medical attention immediately.**\n" +
                "\n" +
                "Do not rely on the App, trainer, heart-rate monitor, power meter, smartwatch, or another connected device to determine whether a symptom is medically significant.\n" +
                "\n" +
                "## 7. Consult a Healthcare Professional\n" +
                "\n" +
                "Before beginning a new exercise program or performing high-intensity or maximal-effort workouts, consider consulting a qualified healthcare professional if you have concerns about your ability to exercise safely.\n" +
                "\n" +
                "This is particularly important if you have a medical condition, cardiovascular risk factors, are taking medication that may affect exercise performance, or have otherwise been advised to limit physical activity.\n" +
                "\n" +
                "## 8. Equipment Safety\n" +
                "\n" +
                "Before each workout:\n" +
                "\n" +
                "* Confirm that your bicycle is correctly installed and secured;\n" +
                "* Confirm that the trainer is correctly installed;\n" +
                "* Follow the trainer manufacturer's instructions;\n" +
                "* Perform required calibration;\n" +
                "* Inspect equipment for damage;\n" +
                "* Confirm that the trainer is functioning normally;\n" +
                "* Ensure adequate ventilation and cooling;\n" +
                "* Have adequate hydration available; and\n" +
                "* Keep children, pets, and other persons away from moving equipment.\n" +
                "\n" +
                "Do not use damaged or improperly installed equipment.\n" +
                "\n" +
                "## 9. Environmental Conditions\n" +
                "\n" +
                "Indoor cycling can produce substantial heat, sweating, dehydration, and fatigue.\n" +
                "\n" +
                "Exercise in an appropriate environment with adequate ventilation and cooling.\n" +
                "\n" +
                "Maintain appropriate hydration and take breaks when necessary.\n" +
                "\n" +
                "Do not exercise when illness, fatigue, dehydration, medication, alcohol, or another condition may impair your ability to exercise safely.\n" +
                "\n" +
                "## 10. Technology and Communication Limitations\n" +
                "\n" +
                "The App communicates with trainers and sensors using supported communication technologies.\n" +
                "\n" +
                "Communication may fail or be interrupted because of:\n" +
                "\n" +
                "* Wireless interference;\n" +
                "* Device distance;\n" +
                "* Hardware problems;\n" +
                "* Firmware problems;\n" +
                "* Operating-system behavior;\n" +
                "* Battery or power problems;\n" +
                "* Software defects; or\n" +
                "* Other technical conditions.\n" +
                "\n" +
                "The App cannot guarantee uninterrupted communication or accurate trainer response.\n" +
                "\n" +
                "## 11. No Guarantee of Accuracy\n" +
                "\n" +
                "Power, cadence, speed, heart-rate, FTP, resistance, training-load, and other measurements may be affected by sensor accuracy, trainer calibration, hardware limitations, software calculations, firmware, environmental conditions, or user-provided information.\n" +
                "\n" +
                "**Do not treat training data as medically validated measurements.**\n" +
                "\n" +
                "## 12. Assumption of Risk and Release\n" +
                "\n" +
                "By using the App for physical exercise, you acknowledge that you understand the risks described above.\n" +
                "\n" +
                "To the maximum extent permitted by applicable law, you voluntarily assume the risks associated with:\n" +
                "\n" +
                "* Cycling;\n" +
                "* Physical exercise;\n" +
                "* High-intensity exercise;\n" +
                "* Maximal-effort exercise;\n" +
                "* Use of smart cycling trainers;\n" +
                "* Automatic resistance control;\n" +
                "* ERG workouts;\n" +
                "* FTP-based workouts; and\n" +
                "* Use of the App to control connected equipment.\n" +
                "\n" +
                "You agree to use reasonable care and to stop exercising whenever continuing would be unsafe.\n" +
                "\n" +
                "Nothing in this document releases or limits liability that cannot legally be released or limited.\n" +
                "\n" +
                "## 13. No Guarantee of Safety\n" +
                "\n" +
                "**No software disclaimer can guarantee that exercise or trainer operation will be safe.**\n" +
                "\n" +
                "The App may include safety limits, warnings, and protective mechanisms. These measures reduce certain risks but cannot eliminate all risks associated with exercise, connected equipment, hardware failures, software failures, or user behavior.\n" +
                "\n" +
                "## 14. User Acknowledgment\n" +
                "\n" +
                "By selecting \"I Agree,\" continuing to use the App after being presented with this notice, or using the App for exercise where permitted by applicable law, you acknowledge that:\n" +
                "\n" +
                "* You have read and understood this Safety Disclaimer;\n" +
                "* You understand that exercise involves inherent risks;\n" +
                "* You understand that the App may automatically control trainer resistance;\n" +
                "* You understand that ERG workouts may increase resistance when cadence decreases;\n" +
                "* You understand that FTP is a training metric and not a medical measurement;\n" +
                "* You will exercise within your physical capabilities;\n" +
                "* You will reduce intensity or stop when necessary;\n" +
                "* You will stop exercising if you experience concerning symptoms; and\n" +
                "* You understand that completing a workout is never more important than your safety.\n" +
                "\n" +
                "**WHEN IN DOUBT, STOP. YOUR SAFETY IS MORE IMPORTANT THAN COMPLETING THE WORKOUT.**\n" +
                "\n" +
                "---\n" +
                "\n" +
                "**[App Name]**\n" +
                "[Legal Contact Email]\n" +
                "[Website URL]\n"

        public const val PRIVACY_POLICY = "# Privacy Policy\n" +
                "\n" +
                "**Effective Date:** [Effective Date]\n" +
                "**Last Updated:** [Last Updated]\n" +
                "\n" +
                "This Privacy Policy explains how **[App Name]** (\"App,\" \"we,\" \"us,\" or \"our\") collects, uses, stores, protects, and shares information when you use the [App Name] mobile application and related services.\n" +
                "\n" +
                "[App Name] is an iOS and Android application designed to connect to and control compatible smart cycling trainers and provide cycling training functionality.\n" +
                "\n" +
                "By using the App, you acknowledge the practices described in this Privacy Policy.\n" +
                "\n" +
                "## 1. Information We Collect\n" +
                "\n" +
                "The information we collect depends on the features you use and the permissions you grant.\n" +
                "\n" +
                "### 1.1 Account Information\n" +
                "\n" +
                "If the App supports user accounts, we may collect:\n" +
                "\n" +
                "* Name or display name;\n" +
                "* Email address;\n" +
                "* Account credentials or authentication identifiers;\n" +
                "* User preferences;\n" +
                "* Account creation and authentication information; and\n" +
                "* Other information necessary to maintain your account.\n" +
                "\n" +
                "If an account is not required for a particular feature, we will not require account information solely to provide that feature unless reasonably necessary.\n" +
                "\n" +
                "### 1.2 Cycling and Training Information\n" +
                "\n" +
                "When you use the App, we may collect or process cycling and training information such as:\n" +
                "\n" +
                "* Power;\n" +
                "* Cadence;\n" +
                "* Speed;\n" +
                "* Distance;\n" +
                "* Duration;\n" +
                "* Workout intervals;\n" +
                "* Workout targets;\n" +
                "* Resistance settings;\n" +
                "* FTP values;\n" +
                "* Training zones;\n" +
                "* Training-load metrics;\n" +
                "* Calories or energy estimates;\n" +
                "* Heart-rate information, when provided by a connected device;\n" +
                "* Trainer status;\n" +
                "* Trainer configuration;\n" +
                "* Calibration information; and\n" +
                "* Workout history.\n" +
                "\n" +
                "Some of this information may be considered sensitive personal or health-related information under applicable laws.\n" +
                "\n" +
                "We use this information only as described in this Privacy Policy and as necessary to provide the functionality you request.\n" +
                "\n" +
                "### 1.3 Connected Device Information\n" +
                "\n" +
                "When you connect a smart trainer, power meter, heart-rate monitor, cadence sensor, or other supported device, we may process information such as:\n" +
                "\n" +
                "* Device name;\n" +
                "* Device manufacturer;\n" +
                "* Device model;\n" +
                "* Device identifier;\n" +
                "* Firmware version;\n" +
                "* Connection status;\n" +
                "* Battery status where available;\n" +
                "* Supported capabilities;\n" +
                "* Trainer measurements; and\n" +
                "* Commands or settings exchanged with the device.\n" +
                "\n" +
                "Bluetooth and other device permissions are used to provide connectivity and trainer-control functionality.\n" +
                "\n" +
                "### 1.4 Health and Fitness Platform Data\n" +
                "\n" +
                "**If supported and only when you authorize access**, the App may interact with platform services such as Apple Health/HealthKit or Android Health Connect.\n" +
                "\n" +
                "Depending on the permissions you grant and the functionality provided, this may include information such as:\n" +
                "\n" +
                "* Heart rate;\n" +
                "* Cycling activity;\n" +
                "* Exercise duration;\n" +
                "* Distance;\n" +
                "* Energy expenditure;\n" +
                "* Weight;\n" +
                "* Other fitness information available through the authorized platform.\n" +
                "\n" +
                "We request only the permissions reasonably necessary for the functionality you choose to use.\n" +
                "\n" +
                "We do not access health information without the permissions required by the applicable operating system and platform.\n" +
                "\n" +
                "### 1.5 Device and Technical Information\n" +
                "\n" +
                "We may automatically receive limited technical information necessary to operate, secure, troubleshoot, and improve the App, such as:\n" +
                "\n" +
                "* Operating-system version;\n" +
                "* App version;\n" +
                "* Device type;\n" +
                "* General device configuration;\n" +
                "* Crash information;\n" +
                "* Diagnostic information;\n" +
                "* Performance information;\n" +
                "* Network information; and\n" +
                "* Security-related information.\n" +
                "\n" +
                "### 1.6 Usage Information\n" +
                "\n" +
                "We may collect information about how you interact with the App, such as:\n" +
                "\n" +
                "* Features used;\n" +
                "* Screens viewed;\n" +
                "* Workout sessions started or stopped;\n" +
                "* Trainer connection events;\n" +
                "* Errors;\n" +
                "* Performance events; and\n" +
                "* Other usage information.\n" +
                "\n" +
                "Where possible, we use aggregated or de-identified information for analytics and product improvement.\n" +
                "\n" +
                "### 1.7 Location Information\n" +
                "\n" +
                "**[REMOVE THIS SECTION IF THE APP DOES NOT COLLECT LOCATION INFORMATION.]**\n" +
                "\n" +
                "If a feature requires location information, we will explain why location is needed and request the applicable operating-system permission.\n" +
                "\n" +
                "We do not collect precise location information unless it is necessary for a feature you have chosen to use.\n" +
                "\n" +
                "## 2. How We Use Information\n" +
                "\n" +
                "We may use information to:\n" +
                "\n" +
                "* Provide and operate the App;\n" +
                "* Connect to and control compatible cycling trainers;\n" +
                "* Execute workouts;\n" +
                "* Calculate training targets and metrics;\n" +
                "* Save workout history;\n" +
                "* Synchronize information across supported devices;\n" +
                "* Provide customer support;\n" +
                "* Diagnose technical problems;\n" +
                "* Detect and prevent fraud, abuse, and security incidents;\n" +
                "* Improve reliability and performance;\n" +
                "* Develop and improve features;\n" +
                "* Maintain and secure our services;\n" +
                "* Comply with legal obligations; and\n" +
                "* Protect the rights, safety, and security of users and others.\n" +
                "\n" +
                "We do not use health or fitness information to provide medical diagnoses or determine whether a user is medically fit to exercise.\n" +
                "\n" +
                "## 3. Health and Fitness Information\n" +
                "\n" +
                "We recognize that health and fitness information can be sensitive.\n" +
                "\n" +
                "We will not use health or fitness information for purposes unrelated to providing, maintaining, securing, or improving the functionality of the App unless permitted by applicable law and appropriately disclosed or authorized.\n" +
                "\n" +
                "**We do not sell your health or fitness information.**\n" +
                "\n" +
                "**We do not use health or fitness information to make medical diagnoses or medical treatment decisions.**\n" +
                "\n" +
                "Where required by applicable law or platform policy, we obtain appropriate consent before collecting or processing health-related information.\n" +
                "\n" +
                "## 4. Advertising and Health Data\n" +
                "\n" +
                "We do not use health or fitness information obtained through Apple Health, HealthKit, Health Connect, or similar health platforms for targeted advertising or advertising profiling.\n" +
                "\n" +
                "We do not provide health or fitness information to advertising networks for advertising purposes.\n" +
                "\n" +
                "If this policy changes, we will update this Privacy Policy and obtain any consent required by applicable law or platform policy.\n" +
                "\n" +
                "## 5. Analytics\n" +
                "\n" +
                "**[KEEP ONLY IF ANALYTICS SDKs ARE ACTUALLY USED.]**\n" +
                "\n" +
                "We may use analytics and diagnostic services to understand application performance and usage.\n" +
                "\n" +
                "Such services may receive limited technical or usage information according to their own privacy policies.\n" +
                "\n" +
                "We configure analytics services to avoid sending health or fitness information unless the disclosure is necessary, lawful, appropriately disclosed, and permitted by the applicable platform rules.\n" +
                "\n" +
                "We maintain an inventory of third-party SDKs used by the App and periodically review the information those SDKs receive.\n" +
                "\n" +
                "## 6. Service Providers\n" +
                "\n" +
                "We may use third-party service providers to operate parts of our service, including:\n" +
                "\n" +
                "* Cloud hosting;\n" +
                "* Authentication;\n" +
                "* Database services;\n" +
                "* Crash reporting;\n" +
                "* Customer support;\n" +
                "* Security services;\n" +
                "* Analytics;\n" +
                "* Communications; and\n" +
                "* Other infrastructure necessary to operate the App.\n" +
                "\n" +
                "Service providers may process information only as necessary to provide services to us, subject to applicable contractual and legal requirements.\n" +
                "\n" +
                "We do not authorize service providers to independently sell your personal information.\n" +
                "\n" +
                "## 7. Legal Requirements and Safety\n" +
                "\n" +
                "We may disclose information when reasonably necessary to:\n" +
                "\n" +
                "* Comply with applicable law;\n" +
                "* Respond to valid legal process;\n" +
                "* Protect the rights or property of [App Name];\n" +
                "* Investigate fraud or security incidents;\n" +
                "* Protect users or the public from serious harm; or\n" +
                "* Establish, exercise, or defend legal claims.\n" +
                "\n" +
                "We disclose only information reasonably necessary for the applicable purpose, subject to applicable law.\n" +
                "\n" +
                "## 8. Business Transfers\n" +
                "\n" +
                "If [App Name] is involved in a merger, acquisition, financing, reorganization, sale of assets, bankruptcy, or similar transaction, personal information may be transferred as part of that transaction.\n" +
                "\n" +
                "Any transfer will remain subject to applicable privacy obligations.\n" +
                "\n" +
                "## 9. Data Retention\n" +
                "\n" +
                "We retain information only for as long as reasonably necessary for the purposes described in this Privacy Policy, including providing services, maintaining workout history, resolving disputes, complying with legal obligations, preventing fraud, and maintaining security.\n" +
                "\n" +
                "Retention periods may differ depending on the type of information.\n" +
                "\n" +
                "For example:\n" +
                "\n" +
                "* Account information may be retained while your account remains active;\n" +
                "* Workout history may be retained while you choose to maintain that history;\n" +
                "* Technical and diagnostic information may be retained for a limited period necessary for security and troubleshooting; and\n" +
                "* Information required for legal or regulatory purposes may be retained for the period required by law.\n" +
                "\n" +
                "When information is no longer required, we delete it, anonymize it, or securely dispose of it where reasonably practicable.\n" +
                "\n" +
                "## 10. Deletion of Your Information\n" +
                "\n" +
                "You may request deletion of personal information associated with your account, subject to applicable legal requirements.\n" +
                "\n" +
                "If the App provides an account-deletion function, you may initiate deletion directly through the App.\n" +
                "\n" +
                "You may also contact:\n" +
                "\n" +
                "**[Privacy Contact Email]**\n" +
                "\n" +
                "We may need to verify your identity before processing a deletion request.\n" +
                "\n" +
                "Some information may need to be retained where required by law, necessary to establish or defend legal claims, or otherwise permitted by applicable law.\n" +
                "\n" +
                "## 11. Access, Correction, and Other Privacy Rights\n" +
                "\n" +
                "Depending on where you live, you may have rights including:\n" +
                "\n" +
                "* Accessing personal information we hold about you;\n" +
                "* Requesting correction of inaccurate information;\n" +
                "* Requesting deletion;\n" +
                "* Requesting information about how your data is used;\n" +
                "* Restricting or objecting to certain processing;\n" +
                "* Withdrawing consent where processing relies on consent; and\n" +
                "* Obtaining a copy of certain information in a portable format.\n" +
                "\n" +
                "Additional rights may apply under California or other applicable privacy laws.\n" +
                "\n" +
                "To exercise applicable rights, contact:\n" +
                "\n" +
                "**[Privacy Contact Email]**\n" +
                "\n" +
                "We will process requests in accordance with applicable law.\n" +
                "\n" +
                "## 12. California Privacy Rights\n" +
                "\n" +
                "If you are a California resident, California law may provide additional rights regarding your personal information, including rights concerning access, deletion, correction, and certain disclosures or uses of personal information.\n" +
                "\n" +
                "Where applicable, California law may provide additional protections for sensitive personal information.\n" +
                "\n" +
                "**We do not sell your personal information or share your personal information for cross-context behavioral advertising unless this Privacy Policy is updated to expressly describe such activity and applicable legal requirements are satisfied.**\n" +
                "\n" +
                "We will not discriminate against you for exercising privacy rights provided by applicable law.\n" +
                "\n" +
                "## 13. Security\n" +
                "\n" +
                "We use reasonable administrative, technical, and organizational safeguards designed to protect personal information against unauthorized access, disclosure, alteration, loss, or destruction.\n" +
                "\n" +
                "Depending on the information and service, safeguards may include:\n" +
                "\n" +
                "* Encryption in transit;\n" +
                "* Encryption at rest where appropriate;\n" +
                "* Access controls;\n" +
                "* Authentication controls;\n" +
                "* Secure credential handling;\n" +
                "* Monitoring and logging;\n" +
                "* Software security updates;\n" +
                "* Dependency management; and\n" +
                "* Incident-response procedures.\n" +
                "\n" +
                "No security system can guarantee absolute security.\n" +
                "\n" +
                "You are also responsible for protecting access to your device and account.\n" +
                "\n" +
                "## 14. Data Breach\n" +
                "\n" +
                "If we determine that a security incident affects personal information and notification is required by applicable law, we will provide notifications as required by law.\n" +
                "\n" +
                "Certain health-data breaches may be subject to additional notification requirements, including requirements under the FTC's Health Breach Notification Rule where applicable.\n" +
                "\n" +
                "## 15. International Data Transfers\n" +
                "\n" +
                "**[REQUIRED IF DATA IS PROCESSED OUTSIDE THE USER'S COUNTRY.]**\n" +
                "\n" +
                "We may process information in countries other than the country in which you live.\n" +
                "\n" +
                "Where required by applicable law, we use appropriate safeguards for international transfers of personal information.\n" +
                "\n" +
                "Users in jurisdictions providing specific transfer rights may contact us for additional information.\n" +
                "\n" +
                "## 16. Children's Privacy\n" +
                "\n" +
                "The App is intended primarily for adults and is not directed to children under 13.\n" +
                "\n" +
                "We do not knowingly collect personal information from children under 13 without legally required authorization.\n" +
                "\n" +
                "If you believe a child has provided personal information to us improperly, contact us at:\n" +
                "\n" +
                "**[Privacy Contact Email]**\n" +
                "\n" +
                "We will take appropriate steps consistent with applicable law.\n" +
                "\n" +
                "## 17. Third-Party Platforms and Devices\n" +
                "\n" +
                "The App may interact with services operated by Apple, Google, smart-trainer manufacturers, sensor manufacturers, and other third parties.\n" +
                "\n" +
                "Your use of those services may be subject to their respective terms and privacy policies.\n" +
                "\n" +
                "We are not responsible for privacy practices outside our control.\n" +
                "\n" +
                "## 18. Apple Health / HealthKit\n" +
                "\n" +
                "**[KEEP IF SUPPORTED.]**\n" +
                "\n" +
                "If you choose to connect [App Name] with Apple Health/HealthKit, the App will access only the categories of information for which you grant permission.\n" +
                "\n" +
                "Health information obtained through HealthKit will be handled in accordance with Apple's applicable requirements and this Privacy Policy.\n" +
                "\n" +
                "HealthKit information will not be used for advertising or sold to advertising platforms.\n" +
                "\n" +
                "We will not use HealthKit information for purposes unrelated to providing the App's requested health and fitness functionality except as permitted by applicable law and Apple's policies.\n" +
                "\n" +
                "## 19. Android Health Connect\n" +
                "\n" +
                "**[KEEP IF SUPPORTED.]**\n" +
                "\n" +
                "If you choose to connect [App Name] with Android Health Connect, the App will access only the categories of information for which you grant permission.\n" +
                "\n" +
                "Health Connect information will be handled in accordance with Google's applicable policies and this Privacy Policy.\n" +
                "\n" +
                "We request only information reasonably necessary for the functionality you choose to use.\n" +
                "\n" +
                "## 20. Bluetooth and Connected Equipment\n" +
                "\n" +
                "The App may use Bluetooth or other supported communication technologies to connect to smart cycling trainers and sensors.\n" +
                "\n" +
                "Device information and measurements received from connected equipment may be processed to:\n" +
                "\n" +
                "* Establish and maintain the connection;\n" +
                "* Control the trainer;\n" +
                "* Execute workouts;\n" +
                "* Display measurements;\n" +
                "* Calculate training metrics;\n" +
                "* Diagnose connection problems; and\n" +
                "* Improve compatibility and reliability.\n" +
                "\n" +
                "The App does not control the privacy practices of the manufacturer of your connected equipment.\n" +
                "\n" +
                "## 21. Changes to This Privacy Policy\n" +
                "\n" +
                "We may update this Privacy Policy when our practices, technology, legal requirements, or services change.\n" +
                "\n" +
                "When material changes occur, we may provide notice through the App, website, email, or other appropriate means.\n" +
                "\n" +
                "The updated Privacy Policy will identify its effective date.\n" +
                "\n" +
                "## 22. Contact Us\n" +
                "\n" +
                "For privacy questions, requests, or concerns, contact:\n" +
                "\n" +
                "**[App Name]**\n" +
                "Privacy Contact: [Privacy Contact Email]\n" +
                "Legal Contact: [Legal Contact Email]\n" +
                "Website: [Website URL]\n" +
                "\n" +
                "## 23. Important Notice About This Policy\n" +
                "\n" +
                "This Privacy Policy describes our intended privacy practices. The actual information collected and processed by the App depends on the features enabled in your version of the App, the permissions you grant, the connected devices you use, and the third-party services integrated into the App.\n" +
                "\n" +
                "We will not intentionally describe data practices in this Privacy Policy that are materially different from the actual operation of the App.\n" +
                "\n" +
                "---\n" +
                "\n" +
                "**By using [App Name], you acknowledge that you have read this Privacy Policy.**\n"

        public const val SESSION_BEGIN_WARNING = "⚠\uFE0F Before You Start\n" +
                "\n" +
                "This workout may automatically increase or decrease your trainer's resistance and may require strenuous effort.\n" +
                "Exercise within your capabilities. If the effort becomes excessive or you feel abnormal symptoms, reduce the intensity or stop immediately.\n" +
                "\n" +
                "FTP and workout targets are training metrics, not medical advice or a measure of your medical fitness.\n" +
                "\n" +
                "Your safety is more important than completing the workout."
    }
}
