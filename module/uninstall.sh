#!/system/bin/sh
PKG="br.com.rootboost.gamespace"
pm uninstall "$PKG" >/dev/null 2>&1 || true
rm -rf /data/adb/.config/RootBoostGameSpace
