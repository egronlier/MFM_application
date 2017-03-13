package gscop.mfm_application;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The type Dessin item 22.
 */
//Cette classe permet de dessiner
public class Dessin_item22 extends View {

    private Bitmap cartographie;
    private final Paint paint = new Paint();

    private String main;

    private HashMap<Integer, Float> mX = new HashMap<>();
    private HashMap<Integer, Float> mY = new HashMap<>();
    private HashMap<Integer, Path> paths = new HashMap<>();
    private ArrayList<Path> completedPaths = new ArrayList<>();

    //tableaux qui contiendront les coordonnées des points
    private ArrayList<Float> tableauX = new ArrayList<>();
    private ArrayList<Float> tableauY = new ArrayList<>();

    private final RectF dirtyRect = new RectF();

    private float xDown;
    private float yDown;
    private ArrayList<Float> xDownList = new ArrayList<>();
    private ArrayList<Float> yDownList = new ArrayList<>();

    /**
     * Instantiates a new Dessin item 22.
     *
     * @param context the context
     */
    public Dessin_item22(Context context) {
        super(context);
    }

    /**
     * Instantiates a new Dessin item 22.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public Dessin_item22(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Instantiates a new Dessin item 22.
     *
     * @param context  the context
     * @param attrs    the attrs
     * @param defStyle the def style
     */
    public Dessin_item22(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        completedPaths = new ArrayList<>();
        // initialise les caractéristiques du trait (forme, couleur...)
        paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(26);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // On transforme le drawable du CD en bitmap
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.item22);

        // On fait en sorte que l'image du CD soit toujours de la même taille quelle que soit la tablette utilisée : l'image est redimensionnée pour être à la bonne taille en fonction de la densité de l'écran
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        float totalDIP_X = metrics.xdpi;
        float totalDIP_Y = metrics.ydpi;
        image = Bitmap.createScaledBitmap (image, (int)(6.6*totalDIP_X), (int)(7.2*totalDIP_Y), false);

        image = image.copy(Bitmap.Config.ARGB_8888, true);

        // On ajoute ce bitmap au canvas pour pouvoir dessiner dessus : les deux nombres en paramètres servent à positionner l'image dans le canvas
        canvas.drawBitmap(image, 0, 0, null);
        canvas = new Canvas(image);


        for (Path fingerPath : paths.values()) {            //dessine ce que l'utilisateur est en train de toucher
            if (fingerPath != null) {
                canvas.drawPoint(xDown, yDown, paint);
                canvas.drawPath(fingerPath, paint);
            }
        }

        for (int i = 0; i < xDownList.size(); i++) {                                   //permet de garder le dessin des points de départ à l'écran(pointer_down)
            canvas.drawPoint(xDownList.get(i), yDownList.get(i), paint);
        }


        for (Path completedPath : completedPaths) {                                     //permet de garder le dessin à l'écran
            canvas.drawPath(completedPath, paint);
        }


        this.cartographie = image;
    }

    //
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int maskedAction = event.getActionMasked();
        int actionIndex = event.getActionIndex();
        int id = event.getPointerId(actionIndex);
        int historySize = event.getHistorySize();

        switch (maskedAction) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                Path p = new Path();
                try {
                    p.moveTo(event.getX(id), event.getY(id));
                    paths.put(id, p);
                    mX.put(id, event.getX(id));
                    mY.put(id, event.getY(id));
                    xDown = event.getX(id);
                    yDown = event.getY(id);
                    tableauX.add(event.getX(id));
                    tableauY.add(event.getY(id));
                    invalidate();
                } catch (IllegalArgumentException ex) {
                    ex.printStackTrace();
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                for (int size = event.getPointerCount(), i = 0; i < size; i++) {  //pour chaque doigt qui touche l'écran
                    Path p = paths.get(event.getPointerId(i));
                    if (p != null) {
                        for (int j = 0; j < historySize; j++) {                       //pour chaque point de l'historique (qui contient les points non pris en compte de ba)
                            float historicalX = event.getHistoricalX(i, j);
                            float historicalY = event.getHistoricalY(i, j);
                            expandDirtyRect(historicalX, historicalY);
                            p.lineTo(historicalX, historicalY);
                            tableauX.add(historicalX);
                            tableauY.add(historicalY);
                        }
                        mX.put(event.getPointerId(i), event.getX(i));
                        mY.put(event.getPointerId(i), event.getY(i));
                        tableauX.add(event.getX(i));
                        tableauY.add(event.getY(i));
                        invalidate();
                    }
                }


                invalidate();
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP: {
                Path p = paths.get(id);
                if (p != null) {
                    completedPaths.add(p);
                    xDownList.add(xDown);
                    yDownList.add(yDown);
                    invalidate();
                    paths.remove(id);
                    mX.remove(id);
                    mY.remove(id);
                }
                invalidate();

                break;
            }
        }

        return true;
    }


    // Cette méthode permet de redimensionner un bitmap
    private static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > 1) {
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float) maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }


    /**
     * Gets cartographie.
     *
     * @return the cartographie
     */
    public Bitmap getCartographie() {
        return cartographie;
    }

    /**
     * Gets tableau x.
     *
     * @return the tableau x
     */
    public ArrayList getTableauX() {
        return tableauX;
    }

    /**
     * Gets tableau y.
     *
     * @return the tableau y
     */
    public ArrayList getTableauY() {
        return tableauY;
    }

    /**
     * Called when replaying history to ensure the dirty region includes all
     * points.
     */
    private void expandDirtyRect(float historicalX, float historicalY) {
        if (historicalX < dirtyRect.left) {
            dirtyRect.left = historicalX;
        } else if (historicalX > dirtyRect.right) {
            dirtyRect.right = historicalX;
        }
        if (historicalY < dirtyRect.top) {
            dirtyRect.top = historicalY;
        } else if (historicalY > dirtyRect.bottom) {
            dirtyRect.bottom = historicalY;
        }
    }

}
