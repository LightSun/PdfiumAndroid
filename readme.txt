
 ============================ jni:

 JNI_FUNC(jobject, PdfiumCore, nativeDeviceCoordsToPage)(JNI_ARGS, jlong pagePtr, jint startX,
                                                         jint startY, jint sizeX,
                                                         jint sizeY, jint rotate, jint deviceX,
                                                         jint deviceY) {
     FPDF_PAGE page = reinterpret_cast<FPDF_PAGE>(pagePtr);
     double pageX, pageY;

     FPDF_DeviceToPage(page, startX, startY, sizeX, sizeY, rotate, deviceX, deviceY, &pageX, &pageY);

     jclass clazz = env->FindClass("android/graphics/PointF");
     jmethodID constructorID = env->GetMethodID(clazz, "<init>", "(FF)V");
     return env->NewObject(clazz, constructorID, pageX, pageY);
 }
 ========================== PdfiumCore.java

 /**
     * Convert the screen coordinates of a point to page coordinates.
     *
     * The page coordinate system has its origin at the left-bottom corner
     * of the page, with the X-axis on the bottom going to the right, and
     * the Y-axis on the left side going up.
     *
     * NOTE: this coordinate system can be altered when you zoom, scroll,
     * or rotate a page, however, a point on the page should always have
     * the same coordinate values in the page coordinate system.
     *
     * The device coordinate system is device dependent. For screen device,
     * its origin is at the left-top corner of the window. However this
     * origin can be altered by the Windows coordinate transformation
     * utilities.
     *
     * You must make sure the start_x, start_y, size_x, size_y
     * and rotate parameters have exactly same values as you used in
     * the FPDF_RenderPage() function call.
     *
     * @param doc       pdf document
     * @param pageIndex index of page
     * @param startX    Left pixel position of the display area in device coordinates.
     * @param startY    Top pixel position of the display area in device coordinates.
     * @param sizeX     Horizontal size (in pixels) for displaying the page.
     * @param sizeY     Vertical size (in pixels) for displaying the page.
     * @param rotate    Page orientation:
     *                      0 (normal)
     *                      1 (rotated 90 degrees clockwise)
     *                      2 (rotated 180 degrees)
     *                      3 (rotated 90 degrees counter-clockwise)
     * @param deviceX   X value in device coordinates to be converted.
     * @param deviceY   Y value in device coordinates to be converted.
     * @return
     */
    public PointF mapDeviceCoordsToPage(PdfDocument doc, int pageIndex, int startX, int startY, int sizeX,
                                           int sizeY, int rotate, int deviceX, int deviceY) {
        long pagePtr = doc.mNativePagesPtr.get(pageIndex);
        return nativeDeviceCoordsToPage(pagePtr, startX, startY, sizeX, sizeY, rotate, deviceX, deviceY);
    }

      private native Point nativePageCoordsToDevice(long pagePtr, int startX, int startY, int sizeX,
                                                       int sizeY, int rotate, double pageX, double pageY);

         private native PointF nativeDeviceCoordsToPage(long pagePtr, int startX, int startY, int sizeX,
                                                        int sizeY, int rotate, int deviceX, int deviceY);