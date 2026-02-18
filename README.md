# comparatif-developpement-mobile

## Structure du dépôt

```text
.
├── adapp-kmp/                  # Code source Kotlin Multiplatform (KMP)
├── adapp-maui/                 # Code source .Net MAUI
├── adapp-react/                # Code source React native
└── comparatif/
    ├── Android/
    │   ├── adapp-kmp/          # Screenshots des résultats (KMP)
    │   ├── adapp-maui/         # Screenshots des résultats (.Net MAUI)
    │   ├── adapp-react/        # Screenshots des résultats (React Native)
    │   ├── adapp-kmp.apk
    │   ├── adapp-kmp.json      # Résultats des tests de performance automatisés (KMP)
    │   ├── adapp-maui.apk
    │   ├── adapp-maui.json     # Résultats des tests de performance automatisés (.Net MAUI)
    │   ├── adapp-react.apk
    │   └── adapp-react.json    # Résultats des tests de performance automatisés (React Native)
    ├── iOS/
    │   ├── adapp-kmp/          # Screenshots des résultats (KMP)
    │   ├── adapp-maui/         # Screenshots des résultats (.Net MAUI)
    │   ├── adapp-react/        # Screenshots des résultats (React Native)
    │   ├── adapp-kmp.ipa
    │   ├── adapp-maui.ipa
    │   └── adapp-react.ipa
    └── maestro.yml             # Script d'automatisation des tests
```

## Résultats Android

Tableau récapitulatif des résultats des test pour les applications Android. L'ordre des valeurs est moyenne, min et max lorsqu'il y en a plusieurs.

|   | .Net Maui | Kotlin Multiplatform | React native |
|---|---|---|---|
| **Application**  |   |   |   |
| Taille (Mo)  | 43,3 | 32,4 | 121,4 |
| **Test CPU**  |   |   |   |
| temps moyen / cycle (ms)  | 181,7 - 176,2 - 187,1 | 251,56 - 247,10 - 256,20 | 4947,07 - 4908,15 - 4967,16 |
| **Test mémoire**  |   |   |   |
| Taux de croissance (MB / cycle)  | 34,8396 - 34,8353 - 34,8428 | 9,6298 - 5,8821 - 15,0519 | 36,2731 - 36,2716 - 36,2759 |
| RMSE (MB)  | 1,6608 - 1,6390 - 1,6935 | 13,3801 - 3,7087 - 18,8839 | 1,0331 - 1,0307 - 1,0339 |
| Empreinte structurelle (MB)  | 8,4469 - 8,3069 - 8,5462 | 29,9579 - 14,5705 - 40,2540 | 6,5559 - 6,5328 - 6,6069 |
| temps moyen / cycle (ms)  | 438,20 - 420,00 - 458,40 | 24,20 - 21,90 - 25,30 | 1276,21 - 1269,75 - 1290,79 |

## Résultats iOS

Tableau récapitulatif des résultats des test pour les applications iOS. L'ordre des valeurs est moyenne, min et max lorsqu'il y en a plusieurs.

|   | .Net Maui | Kotlin Multiplatform | React native |
|---|---|---|---|
| **Application**  |   |   |   |
| Taille (Mo)  | 25,6 | 18,3 | 18,7 |
| **Test CPU**  |   |   |   |
| temps moyen / cycle (ms)  | 23,78 - 23,30 - 24,20 | 20,54 - 19,80 - 22,40 | 7599,90 - 7596,53 - 7604,88 |
| **Test mémoire**  |   |   |   |
| Taux de croissance (MB / cycle)  | 34,3645 - 34,3632 - 34,3662 | 49,9574 - 49,4688 - 50,1929 | 36,0340 - 35,9538 - 36,1547 |
| RMSE (MB)  | 1,2738 - 1,2708 - 1,2770 | 5,9714 - 4,4557 - 6,6335 | 0,4989 - 0,2446 - 0,8794 |
| Empreinte structurelle (MB)  | 4,4564 - 4,4368 - 4,4691 | 261,7714 - 260,2937 - 264,3906 | 7,9446 - 7,1958 - 8,8399 |
| temps moyen / cycle (ms)  | 76,92 - 75,00 - 79,00 | 83,76 - 79,00 - 88,90 | 314,37 - 312,56 - 316,01 |