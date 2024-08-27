import SwiftUI
import shared

struct ContentView: UIViewControllerRepresentable {
    let rootComponent: RootComponent

    func makeUIViewController(context: Context) -> UIViewController {
        App_iosKt.AppStart(rootComponent:rootComponent)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
