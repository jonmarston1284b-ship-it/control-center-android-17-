package com.custom.controlcenter;

import android.accessibilityservice.AccessibilityService;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;

public class ControlCenterService extends AccessibilityService {

    private WindowManager windowManager;
    private View controlCenterView;
    private boolean isExpanded = false;

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        createControlCenterLayout();
    }

    private void createControlCenterLayout() {
        LayoutInflater inflater = LayoutInflater.from(this);
        // Inflate custom layout styled like Android 17's unified panel
        controlCenterView = inflater.inflate(R.layout.layout_control_center, null);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY, // Draws over other apps safely
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | 
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT
        );

        params.gravity = Gravity.TOP;
        params.y = -1000; // Hidden initially off-screen
    }

    public void expandPanel() {
        if (!isExpanded && controlCenterView != null) {
            isExpanded = true;
        }
    }

    public void collapsePanel() {
        if (isExpanded && controlCenterView != null) {
            isExpanded = false;
        }
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // Capture specific system events here if needed
    }

    @Override
    public void onInterrupt() {
        if (controlCenterView != null && windowManager != null) {
            windowManager.removeView(controlCenterView);
        }
    }
}
