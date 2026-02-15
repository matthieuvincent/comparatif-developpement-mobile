import SwiftUI
import Shared

struct KmpViewControllerBridge: UIViewControllerRepresentable {
    let makeController: () -> UIViewController

    func makeUIViewController(context: Context) -> UIViewController {
        return makeController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}