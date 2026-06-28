import androidx.compose.ui.window.ComposeUIViewController
import com.skjline.fitness.feature.registration.presentation.RegistrationScreen
import com.skjline.fitness.presentation.main.MainScreen
import com.skjline.fitness.presentation.session.SessionScreen

fun mainViewController() = ComposeUIViewController {
    MainScreen(link = "")
}

fun sessionViewController(path: String) = ComposeUIViewController {
    SessionScreen(path)
}

fun registrationViewController() = ComposeUIViewController {
    RegistrationScreen()
}

//
//(void) showModal: (NSString *) message {
//UIAlertController* alert = [
//    UIAlertController alertControllerWithTitle: NSLocalizedString(@"Information", @"Title")
//        message: message
//        preferredStyle: UIAlertControllerStyleAlert
//    ];
//
//    UIAlertAction* action = [UIAlertAction actionWithTitle: NSLocalizedString(@"Ok", @"Okay")
//        style: UIAlertActionStyleDefault
//        handler: ^(UIAlertAction* action) {}
//    ];
//
//    [alert addAction: action];
//    [self presentViewController: alert animate: YES completion: nil];
//
//    [self setProcessingRequest: NO];
//    [s]
//
//}
