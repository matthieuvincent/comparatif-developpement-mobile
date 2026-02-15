import SwiftUI
import Shared

struct AccText: View {
    let accessibleText: AccessibleText

    var body: some View {
        Text(accessibleText.visual)
            .accessibilityLabel(accessibleText.accessibility)
    }
}

struct Title: View {
    var accessibleText: AccessibleText

    var body: some View {
        AccText(accessibleText: accessibleText)
            .font(.system(size: 20, weight: .bold))
    }
}

struct CardStyle: ViewModifier {
    func body(content: Content) -> some View {
        content
            .padding(15)
            .frame(maxWidth: .infinity, alignment: .leading)
            .background(Color.white)
            .cornerRadius(10)
            .overlay(RoundedRectangle(cornerRadius: 10).stroke(Color.gray.opacity(0.3), lineWidth: 1))
            .shadow(color: Color.black.opacity(0.1), radius: 4, x: 0, y: 2)
    }
}