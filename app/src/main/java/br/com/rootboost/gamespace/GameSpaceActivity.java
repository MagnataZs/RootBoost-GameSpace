package br.com.rootboost.gamespace;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameSpaceActivity extends Activity {
    private GameSpaceView view;
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        view = new GameSpaceView(this, getGames(), this::launchSelected);
        setContentView(view);
    }

    @Override protected void onResume() {
        super.onResume();
        if (view != null) view.refreshStatus();
    }

    private List<GameInfo> getGames() {
        Intent query = new Intent(Intent.ACTION_MAIN);
        query.addCategory("android.intent.category.GAME");
        List<ResolveInfo> found = getPackageManager().queryIntentActivities(query, PackageManager.MATCH_ALL);
        ArrayList<GameInfo> games = new ArrayList<>();
        for (ResolveInfo info : found) {
            ApplicationInfo app = info.activityInfo.applicationInfo;
            if (app.packageName.equals(getPackageName())) continue;
            String label = String.valueOf(info.loadLabel(getPackageManager()));
            games.add(new GameInfo(label, app.packageName, info.loadIcon(getPackageManager())));
        }
        games.sort(Comparator.comparing(g -> g.label.toLowerCase()));
        return games;
    }

    private void launchSelected(GameInfo game) {
        if (game == null) return;
        Intent launch = getPackageManager().getLaunchIntentForPackage(game.packageName);
        if (launch != null) {
            launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(launch);
        }
    }

    public static final class GameInfo {
        public final String label;
        public final String packageName;
        public final android.graphics.drawable.Drawable icon;
        GameInfo(String label, String packageName, android.graphics.drawable.Drawable icon) {
            this.label = label; this.packageName = packageName; this.icon = icon;
        }
    }
}
