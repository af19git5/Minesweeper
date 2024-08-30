import SwiftUI
import shared

struct ContentView: UIViewControllerRepresentable {
    let rootComponent: RootComponent

    func makeUIViewController(context: Context) -> UIViewController {
        let viewController = App_iosKt.AppStart(rootComponent: rootComponent)
        addTapGestureToDismissKeyboard(view: viewController.view)
        return viewController
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}

    private func addTapGestureToDismissKeyboard(view: UIView) {
        let tapGesture = UITapGestureRecognizer(target: view, action: #selector(UIView.endEditing))
        tapGesture.cancelsTouchesInView = false
        view.addGestureRecognizer(tapGesture)
    }
}
