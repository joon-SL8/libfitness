Android
Android apps can use an Implicit Intent to redirect users to the GET https://www.strava.com/oauth/mobile/authorize endpoint. The Strava app should open automatically if the user has it installed.

Sample code:

val intentUri = Uri.parse("https://www.strava.com/oauth/mobile/authorize")
.buildUpon()
.appendQueryParameter("client_id", "1234321")
.appendQueryParameter("redirect_uri", "https://www.yourapp.com")
.appendQueryParameter("response_type", "code")
.appendQueryParameter("approval_prompt", "auto")
.appendQueryParameter("scope", "activity:write,read")
.build()

val intent = Intent(Intent.ACTION_VIEW, intentUri)
startActivity(intent)


iOS
If the user does not have Strava installed, your app should use SFAuthenticationSession or ASWebAuthenticationSession, depending on which versions of iOS your app supports. If your app is linked on or after iOS 9.0, you must add strava in you app’s Info.plist file. It should be added under the LSApplicationQueriesSchemes key. Failure to do this will result in a crash when calling canOpenUrl:.

You should check first if your app can open the appOAuthURLStravaScheme. If it can, the Strava app is installed and should handle the authentication. You can proceed by opening that same URL.

If your app is not able to open that URL, proceed with using the webOAuthUrl. You should use SFAuthenticationSessions or ASWebAuthenticationSession to prevent the user from leaving your application to authenticate. Ensure that the callbackURLScheme is a registered url scheme in your application.

Sample code:

private var authSession: SFAuthenticationSession?

let appOAuthUrlStravaScheme = URL(string: "strava://oauth/mobile/authorize?client_id=1234321&redirect_uri=YourApp%3A%2F%2Fwww.yourapp.com%2Fen-US&response_type=code&approval_prompt=auto&scope=activity%3Awrite%2Cread&state=test")!

let webOAuthUrl = URL(string: "https://www.strava.com/oauth/mobile/authorize?client_id=1234321&redirect_uri= YourApp%3A%2F%2Fwww.yourapp.com%2Fen-US&response_type=code&approval_prompt=auto&scope=activity%3Awrite%2Cread&state=test")!

@IBAction func authenticate() {
if UIApplication.shared.canOpenURL(appOAuthUrlstravaScheme) {
UIApplication.shared.open(appOAuthUrlstravaScheme, options: [:])
} else {
authSession = SFAuthenticationSession(url: webOAuthUrl, callbackURLScheme: "YourApp://") { url, error in

        }

        authSession?.start()
    }
}