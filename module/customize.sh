#!/system/bin/sh
SKIPUNZIP=1
PKG="br.com.rootboost.gamespace"
ui_print "- Instalando RootBoost Game Space"
mkdir -p "$MODPATH"
unzip -o "$ZIPFILE" 'module.prop' -d "$MODPATH" >&2
unzip -o "$ZIPFILE" 'RootBoostGameSpace.apk' -d "$MODPATH" >&2
if [ ! -f "$MODPATH/RootBoostGameSpace.apk" ]; then
  abort "APK do painel não encontrado no módulo."
fi
chmod 0644 "$MODPATH/RootBoostGameSpace.apk"
if pm install -r -d --user 0 "$MODPATH/RootBoostGameSpace.apk" >/dev/null 2>&1; then
  ui_print "- APK do painel instalado"
else
  ui_print "! Não foi possível instalar o APK automaticamente"
  ui_print "! Instale RootBoostGameSpace.apk manualmente"
fi
ui_print "- Módulo instalado sem daemon persistente"
