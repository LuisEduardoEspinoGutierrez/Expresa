package com.example.tt2.ejercicios;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.*;

public class SopaDeLetrasView extends View {

    private final int rows = 12;
    private final int cols = 12;

    private char[][] grid;
    private float cellSize, offsetX, offsetY;

    private Paint textPaint, cellPaint, selectPaint, foundPaint, gridPaint;

    private int sr, sc, er, ec;
    private boolean selecting = false;

    private List<Word> words = new ArrayList<>();
    private Set<Integer> found = new HashSet<>();

    public interface OnWordFoundListener {
        void onWordFound(String word);
    }

    private OnWordFoundListener listener;

    public SopaDeLetrasView(Context c, AttributeSet a) {
        super(c, a);
        init();
    }

    private void init() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setFakeBoldText(true);
        textPaint.setColor(Color.BLACK);

        cellPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        cellPaint.setColor(Color.WHITE);
        cellPaint.setStyle(Paint.Style.FILL);

        gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        gridPaint.setColor(Color.parseColor("#CCCCCC"));
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setStrokeWidth(3f);

        selectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selectPaint.setColor(Color.parseColor("#8038A9F5"));

        foundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        foundPaint.setColor(Color.parseColor("#804CAF50"));

        loadGrid();
    }

    private void loadGrid() {
        // Rediseño a 12x12 para que las letras sean mucho más grandes en móvil
        String[] template = {
                "TRICICLOXXXX", // 0
                "TRENZATRONCO", // 1
                "RXTRENXXXTXX", // 2
                "UXTROFEOXRTX", // 3
                "CXXXXXXXXERS", // 4
                "HXTRACTORSEA", // 5
                "AXTROMPAXNBS", // 6
                "XMATRIMONIOT", // 7
                "ESTRELLAXXLR", // 8
                "TROMPETAXXXE", // 9
                "MAESTRAXXXXX", // 10
                "XXXXXXXXXXXX"  // 11
        };

        grid = new char[rows][cols];
        Random random = new Random();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                char ch = template[r].charAt(c);
                if (ch == 'X') {
                    grid[r][c] = (char) ('A' + random.nextInt(26));
                } else {
                    grid[r][c] = ch;
                }
            }
        }

        words.clear();
        // Horizontales
        add("TRICICLO", 0, 0, 0, 7);
        add("TRENZA", 1, 0, 1, 5);
        add("TRONCO", 1, 6, 1, 11);
        add("TREN", 2, 2, 2, 5);
        add("TROFEO", 3, 2, 3, 7);
        add("TRACTOR", 5, 2, 5, 8);
        add("TROMPA", 6, 2, 6, 7);
        add("MATRIMONIO", 7, 1, 7, 10);
        add("ESTRELLA", 8, 0, 8, 7);
        add("TROMPETA", 9, 0, 9, 7);
        add("MAESTRA", 10, 0, 10, 6);

        // Verticales
        add("TRUCHA", 1, 0, 6, 0);
        add("TRES", 2, 9, 5, 9);
        add("TREBOL", 3, 10, 8, 10);
        add("SASTRE", 4, 11, 9, 11);
    }

    private void add(String w, int sr, int sc, int er, int ec) {
        words.add(new Word(w, sr, sc, er, ec));
    }

    public void setOnWordFoundListener(OnWordFoundListener l) {
        listener = l;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        cellSize = Math.min(w / (float) cols, h / (float) rows);
        offsetX = (w - cols * cellSize) / 2;
        offsetY = (h - rows * cellSize) / 2;
        textPaint.setTextSize(cellSize * 0.7f);
    }

    @Override
    protected void onDraw(Canvas c) {
        super.onDraw(c);
        c.drawRect(offsetX, offsetY, offsetX + cols * cellSize, offsetY + rows * cellSize, cellPaint);

        for (int i : found) {
            drawSelection(c, words.get(i).sr, words.get(i).sc, words.get(i).er, words.get(i).ec, foundPaint);
        }

        if (selecting) {
            drawSelection(c, sr, sc, er, ec, selectPaint);
        }

        for (int r = 0; r <= rows; r++) {
            float y = offsetY + r * cellSize;
            c.drawLine(offsetX, y, offsetX + cols * cellSize, y, gridPaint);
        }
        for (int col = 0; col <= cols; col++) {
            float x = offsetX + col * cellSize;
            c.drawLine(x, offsetY, x, offsetY + rows * cellSize, gridPaint);
        }

        for (int r = 0; r < rows; r++) {
            for (int col = 0; col < cols; col++) {
                float x = offsetX + col * cellSize;
                float y = offsetY + r * cellSize;
                Paint.FontMetrics fm = textPaint.getFontMetrics();
                float ty = y + cellSize / 2 - (fm.ascent + fm.descent) / 2;
                c.drawText("" + grid[r][col], x + cellSize / 2, ty, textPaint);
            }
        }
    }

    private void drawSelection(Canvas c, int r1, int c1, int r2, int c2, Paint p) {
        float x1 = offsetX + Math.min(c1, c2) * cellSize;
        float y1 = offsetY + Math.min(r1, r2) * cellSize;
        float x2 = offsetX + (Math.max(c1, c2) + 1) * cellSize;
        float y2 = offsetY + (Math.max(r1, r2) + 1) * cellSize;
        c.drawRect(x1, y1, x2, y2, p);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        int col = (int) ((x - offsetX) / cellSize);
        int row = (int) ((y - offsetY) / cellSize);

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (row >= 0 && row < rows && col >= 0 && col < cols) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    sr = row; sc = col; er = row; ec = col;
                    selecting = true;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (selecting) {
                    er = Math.max(0, Math.min(row, rows - 1));
                    ec = Math.max(0, Math.min(col, cols - 1));
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (selecting) {
                    checkWord();
                    selecting = false;
                    getParent().requestDisallowInterceptTouchEvent(false);
                    invalidate();
                }
                break;
        }
        return true;
    }

    private void checkWord() {
        for (int i = 0; i < words.size(); i++) {
            Word w = words.get(i);
            if (((sr == w.sr && sc == w.sc) && (er == w.er && ec == w.ec)) ||
                ((sr == w.er && sc == w.ec) && (er == w.sr && ec == w.sc))) {
                if (!found.contains(i)) {
                    found.add(i);
                    if (listener != null) listener.onWordFound(w.word);
                }
            }
        }
    }

    private static class Word {
        String word;
        int sr, sc, er, ec;
        Word(String w, int sr, int sc, int er, int ec) {
            this.word = w; this.sr = sr; this.sc = sc; this.er = er; this.ec = ec;
        }
    }
}
