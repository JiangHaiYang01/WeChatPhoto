package com.starot.larger.view.image;

/**
 * Interface definition for a callback to be invoked when the photo is experiencing a drag event
 */
public interface OnViewDragListener {

    /**
     * Callback for when the photo is experiencing a drag event. This cannot be invoked when the
     * user is scaling.
     *
     * @param dx The change of the coordinates in the x-direction
     * @param dy The change of the coordinates in the y-direction
     */
    void onDrag(float dx, float dy);


    /**
     * A callback to receive where the user taps on a ImageView. You will receive a callback if
     * the user taps anywhere on the view, dragging on 'whitespace' will not be ignored.
     *
     * @param x    - where the user dragged from the left of the View.
     * @param y    - where the user dragged from the top of the View.
     */
    void onScroll(float x, float y);

    void onScrollFinish();

    void onScrollStart();
}
