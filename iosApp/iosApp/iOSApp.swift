import SwiftUI
import shared

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self)
    var appDelegate: AppDelegate

	var body: some Scene {
		WindowGroup {
			ContentView(rootComponent: appDelegate.rootComponent)
			    .ignoresSafeArea(.keyboard)
		}
	}
}

class AppDelegate: NSObject, UIApplicationDelegate {
    let rootComponent: RootComponent = RootComponent(
        context: DefaultComponentContext(lifecycle: ApplicationLifecycle())
    )
}
