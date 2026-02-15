import SwiftUI
import Shared

struct QRCodeView: View {
    var body: some View {
        ScrollView {
            VStack(spacing: 15) {
                Title(accessibleText: Labels.shared.QR_TITLE).frame(maxWidth: .infinity, alignment: .leading)
                KmpViewControllerBridge(makeController: MainViewControllerKt.QrCodeController).frame(width: 300, height: 300, alignment: .center)
            }
            .padding(EdgeInsets(top: 40, leading: 20, bottom: 20, trailing: 20))
        }
    }
}
