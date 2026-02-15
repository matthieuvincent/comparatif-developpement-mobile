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

Tableau récapitulatif des résultats des test pour les applications Android. L'ordre des valeurs est la moyenne, la valeur minimale et la valeur maximale.

|   | .Net Maui | Kotlin Multiplatform | React native |
|---|---|---|---|
| **Test CPU**  |   |   |   |
| temps moyen / cycle (ms)  | 181,7 - 176,2 - 187,1 | 40,32 - 36,9 - 45,9 | 4947,07 - 4908,15 - 4967,16 |
| **Test mémoire**  |   |   |   |
| Taux de croissance (MB / cycle)  | 34,8396 - 34,8353 - 34,8428 | 5,3305 - 0,9553 - 8,2530 | 36,2731 - 36,2716 - 36,2759 |
| RMSE (MB)  | 1,6608 - 1,6390 - 1,6935 | 15,4184 - 9,2103 - 20,6959 | 1,0331 - 1,0307 - 1,0339 |
| Empreinte structurelle (MB)  | 8,4469 - 8,3069 - 8,5462 | 49,1784 - 32,2186 - 60,8406 | 6,5559 - 6,5328 - 6,6069 |
| temps moyen / cycle (ms)  | 438,20 - 420,00 - 458,40 | 25,76 - 24,30 - 27,60 | 1276,21 - 1269,75 - 1290,79 |

## Résultats iOS

Tableau récapitulatif des résultats des test pour les applications iOS. L'ordre des valeurs est la moyenne, la valeur minimale et la valeur maximale.

[moyenne - min - max]

|   | .Net Maui | Kotlin Multiplatform | React native |
|---|---|---|---|
| **Test CPU**  |   |   |   |
| temps moyen / cycle (ms)  | 23,78 - 23,30 - 24,20 | 9,42 - 9,00 - 10,00 | 7599,90 - 7596,53 - 7604,88 |
| **Test mémoire**  |   |   |   |
| Taux de croissance (MB / cycle)  | 34,3645 - 34,3632 - 34,3662 | 49,0481 - 48,9921 - 49,1908 | 36,0340 - 35,9538 - 36,1547 |
| RMSE (MB)  | 1,2738 - 1,2708 - 1,2770 | 6,2315 - 4,3693 - 6,8521 | 0,4989 - 0,2446 - 0,8794 |
| Empreinte structurelle (MB)  | 4,4564 - 4,4368 - 4,4691 | 267,2314 - 265,3281 - 274,5448 | 7,9446 - 7,1958 - 8,8399 |
| temps moyen / cycle (ms)  | 76,92 - 75,00 - 79,00 | 82,58 - 76,90 - 93,80 | 314,37 - 312,56 - 316,01 |