package br.com.rootboost.gamespace;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.view.MotionEvent;
import android.view.View;
import java.util.List;
import java.util.Locale;

public final class GameSpaceView extends View {
    public interface OnPlay { void play(GameSpaceActivity.GameInfo game); }
    private final List<GameSpaceActivity.GameInfo> games;
    private final OnPlay onPlay;
    private final Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int selected = 0;
    private boolean settings = false;
    private float density;
    private float temperature = -1;
    private int battery = -1;
    private boolean root = false;
    private RectF playRect = new RectF();
    private RectF[] gameRects = new RectF[0];

    GameSpaceView(Context context, List<GameSpaceActivity.GameInfo> games, OnPlay onPlay) {
        super(context);
        this.games = games;
        this.onPlay = onPlay;
        density = getResources().getDisplayMetrics().density;
        p.setTypeface(android.graphics.Typeface.create("sans", android.graphics.Typeface.NORMAL));
        setFocusable(true);
        refreshStatus();
    }

    private float d(float value) { return value * density; }
    private void text(Canvas c, String value, float x, float y, float size, int color, boolean bold) {
        p.setStyle(Paint.Style.FILL); p.setColor(color); p.setTextSize(d(size));
        p.setTypeface(android.graphics.Typeface.create("sans", bold ? 1 : 0));
        c.drawText(value, d(x), d(y), p);
    }
    private void round(Canvas c, float l, float t, float r, float b, float radius, int color) {
        p.setStyle(Paint.Style.FILL); p.setColor(color); c.drawRoundRect(new RectF(d(l), d(t), d(r), d(b)), d(radius), d(radius), p);
    }
    private void line(Canvas c, float l, float t, float r, float b, int color) {
        p.setStyle(Paint.Style.FILL); p.setColor(color); c.drawRect(d(l), d(t), d(r), d(b), p);
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float w = getWidth() / density;
        float h = getHeight() / density;
        int bg = Color.rgb(20, 15, 22);
        canvas.drawColor(bg);
        GameSpaceActivity.GameInfo game = games.isEmpty() ? null : games.get(Math.min(selected, games.size() - 1));
        int accent = Color.rgb(242, 140, 155);
        int panel = Color.argb(215, 39, 27, 39);
        int soft = Color.rgb(196, 177, 190);
        int white = Color.rgb(252, 245, 248);

        if (game != null) {
            int color = colorFor(game);
            p.setShader(new android.graphics.LinearGradient(0, 0, 0, getHeight(), color, bg, android.graphics.Shader.TileMode.CLAMP));
            canvas.drawRect(0, 0, getWidth(), getHeight(), p);
            p.setShader(null);
            line(canvas, 0, 0, w, h, Color.argb(95, 0, 0, 0));
        }

        text(canvas, "Jogos", 28, 37, 20, settings ? soft : white, true);
        text(canvas, "Configurações", 112, 37, 18, settings ? white : soft, false);
        text(canvas, "ROOTBOOST", w - 148, 34, 16, white, true);
        text(canvas, "GAME SPACE", w - 148, 53, 10, accent, true);
        text(canvas, "⌕", w - 67, 38, 26, white, false);
        text(canvas, "⏻", w - 35, 38, 21, soft, false);
        text(canvas, currentTime(), w - 185, 82, 13, white, true);
        text(canvas, battery >= 0 ? battery + "%" : "—", w - 80, 82, 13, white, true);
        text(canvas, temperature >= 0 ? String.format(Locale.US, "%.0f°C", temperature) : "—°C", w - 142, 82, 12, soft, false);
        line(canvas, 24, 93, w - 24, 94, Color.argb(90, 255, 255, 255));

        if (settings) drawSettings(canvas, w, h, accent, panel, soft, white);
        else drawGames(canvas, w, h, accent, panel, soft, white, game);
    }

    private void drawGames(Canvas c, float w, float h, int accent, int panel, int soft, int white, GameSpaceActivity.GameInfo game) {
        gameRects = new RectF[games.size()];
        float x = 25;
        float y = 114;
        for (int i = 0; i < games.size(); i++) {
            GameSpaceActivity.GameInfo item = games.get(i);
            float size = i == selected ? 78 : 62;
            float top = y + (78 - size) / 2f;
            gameRects[i] = new RectF(d(x), d(top), d(x + size), d(top + size));
            round(c, x - 3, top - 3, x + size + 3, top + size + 3, 19, i == selected ? accent : Color.argb(150, 25, 18, 26));
            drawIcon(c, item.icon, x, top, size);
            x += size + 17;
            if (x + 65 > w - 20) break;
        }
        if (game == null) {
            text(c, "Nenhum jogo encontrado", 28, 280, 24, white, true);
            text(c, "Instale um jogo com categoria de jogo para aparecer aqui.", 28, 310, 13, soft, false);
            return;
        }
        round(c, 24, 230, w - 24, h - 112, 24, panel);
        drawIcon(c, game.icon, 42, 253, 58);
        text(c, game.label, 115, 278, game.label.length() > 25 ? 22 : 28, white, true);
        text(c, version(game), 115, 303, 12, soft, false);
        text(c, game.packageName, 115, 323, 12, soft, false);
        text(c, "Perfil atual", 42, h - 160, 11, soft, false);
        text(c, "Equilibrado", 42, h - 137, 15, white, true);
        text(c, "Root", 178, h - 160, 11, soft, false);
        text(c, root ? "Concedido" : "Não verificado", 178, h - 137, 15, root ? Color.rgb(135, 224, 177) : Color.rgb(255, 190, 130), true);
        text(c, "Módulo", 330, h - 160, 11, soft, false);
        text(c, "Pronto", 330, h - 137, 15, white, true);
        playRect = new RectF(d(w - 190), d(h - 105), d(w - 24), d(h - 30));
        round(c, w - 190, h - 105, w - 24, h - 30, 25, Color.rgb(246, 171, 181));
        text(c, "▶  Jogar", w - 157, h - 59, 22, Color.rgb(100, 42, 54), true);
        text(c, "✎", w - 237, h - 67, 25, soft, false);
        text(c, "⚙", w - 207, h - 67, 23, soft, false);
    }

    private void drawSettings(Canvas c, float w, float h, int accent, int panel, int soft, int white) {
        round(c, 24, 112, 205, h - 25, 22, Color.argb(185, 38, 27, 39));
        String[] cats = {"Geral", "Desempenho", "Aparência", "Áudio"};
        for (int i = 0; i < cats.length; i++) {
            if (i == 0) round(c, 36, 132 + i * 58, 193, 178 + i * 58, 15, accent);
            text(c, cats[i], 54, 161 + i * 58, 15, i == 0 ? Color.WHITE : soft, i == 0);
        }
        round(c, 225, 112, w - 24, h - 25, 22, panel);
        text(c, "Geral", 255, 157, 28, white, true);
        text(c, "Configurações do Game Space", 255, 182, 13, soft, false);
        setting(c, "Iniciar monitoramento automaticamente", "Desligado por padrão", 225, 220, accent, soft, white, false);
        setting(c, "Restaurar ajustes ao fechar o jogo", "Recomendado", 225, 294, accent, soft, white, true);
        setting(c, "Vibração ao tocar", "Feedback visual e tátil", 225, 368, accent, soft, white, false);
        text(c, "As funções avançadas serão adicionadas por sessão, sem daemon permanente.", 255, h - 60, 12, soft, false);
    }

    private void setting(Canvas c, String title, String sub, float x, float y, int accent, int soft, int white, boolean on) {
        text(c, title, x + 28, y, 15, white, true);
        text(c, sub, x + 28, y + 23, 12, soft, false);
        round(c, 600, y - 20, 651, y + 11, 16, on ? accent : Color.rgb(80, 65, 78));
        p.setColor(Color.WHITE); p.setStyle(Paint.Style.FILL); c.drawCircle(d(on ? 635 : 616), d(y - 4), d(10), p);
    }

    private void drawIcon(Canvas c, Drawable icon, float x, float y, float size) {
        if (icon == null) return;
        icon.setBounds((int)d(x), (int)d(y), (int)d(x + size), (int)d(y + size));
        icon.draw(c);
    }

    private int colorFor(GameSpaceActivity.GameInfo game) {
        int hash = Math.abs(game.packageName.hashCode());
        return Color.rgb(45 + hash % 55, 25 + (hash / 7) % 45, 48 + (hash / 13) % 70);
    }
    private String version(GameSpaceActivity.GameInfo game) {
        try { return "Versão instalada • " + getContext().getPackageManager().getPackageInfo(game.packageName, 0).versionName; }
        catch (Exception e) { return "Aplicativo instalado"; }
    }
    private String currentTime() { return new java.text.SimpleDateFormat("HH:mm", Locale.getDefault()).format(new java.util.Date()); }

    void refreshStatus() {
        try {
            BatteryManager bm = (BatteryManager)getContext().getSystemService(Context.BATTERY_SERVICE);
            battery = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
            android.content.Intent i = getContext().registerReceiver(null, new android.content.IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            if (i != null) temperature = i.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) / 10f;
            root = Runtime.getRuntime().exec(new String[]{"su", "-c", "id"}).waitFor() == 0;
        } catch (Exception ignored) { root = false; }
        invalidate();
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_UP) return true;
        float x = event.getX() / density, y = event.getY() / density;
        float w = getWidth() / density;
        if (y < 65 && x > 95 && x < 245) { settings = true; invalidate(); return true; }
        if (y < 65 && x < 95) { settings = false; invalidate(); return true; }
        if (!settings) {
            for (int i = 0; i < gameRects.length; i++) if (gameRects[i].contains(event.getX(), event.getY())) { selected = i; invalidate(); return true; }
            if (playRect.contains(event.getX(), event.getY()) && !games.isEmpty()) { onPlay.play(games.get(selected)); return true; }
        }
        return true;
    }
}
