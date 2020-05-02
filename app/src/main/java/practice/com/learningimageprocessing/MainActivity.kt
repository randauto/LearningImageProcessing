package practice.com.learningimageprocessing

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import practice.com.learningimageprocessing.utils.ImageProcessingUtils
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ivSource?.setImageDrawable(resources.getDrawable(R.drawable.dog));

        val task = MyTask(this, resources.getDrawable(R.drawable.dog))
        task.execute()
    }

    class MyTask internal constructor(val context: MainActivity?, val drawable: Drawable?) :
        AsyncTask<Void, Void, Bitmap>() {
        private val activityReference: WeakReference<MainActivity?> = WeakReference(context)

        override fun onPreExecute() {
            super.onPreExecute()
            val activity = activityReference.get()
            if (activity == null || activity.isFinishing) {
                return
            }
            activity?.progressBar?.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg p0: Void?): Bitmap {
            val bitmap = ImageProcessingUtils.getBitmapFromDrawable(context, drawable)

            return ImageProcessingUtils.rotate(bitmap, 45.0f);
        }

        override fun onPostExecute(result: Bitmap?) {
            result?.let {
                val activity: MainActivity? = activityReference.get()
                if (activity == null || activity.isFinishing) {
                    return
                }
                activity.progressBar?.visibility = View.GONE
                activity.ivDest?.setImageBitmap(result)

                super.onPostExecute(result)
            }
        }

    }
}
