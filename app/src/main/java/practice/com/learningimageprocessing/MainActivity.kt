package practice.com.learningimageprocessing

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import practice.com.learningimageprocessing.utils.ImageProcessingUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ivSource?.setImageDrawable(resources.getDrawable(R.drawable.dog));

        val task = MyTask(this, resources.getDrawable(R.drawable.dog))
        task.execute()
    }

    class MyTask internal constructor(val context: Activity?, val drawable: Drawable?) :
        AsyncTask<Void, Void, Bitmap>() {

        override fun doInBackground(vararg p0: Void?): Bitmap {
            val bitmap = ImageProcessingUtils.getBitmapFromDrawable(context, drawable)

            return ImageProcessingUtils.rotate(bitmap, 45.0f);
        }

        override fun onPostExecute(result: Bitmap?) {
            result?.let {
                context?.let {
                    context.runOnUiThread(object : Runnable {
                        override fun run() {
                            context.ivDest?.setImageBitmap(result)
                        }

                    })
                }
            }

            super.onPostExecute(result)
        }

    }
}
