package dev.android.firebasetest

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dev.android.firebasetest.ui.theme.FireBaseTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FireBaseTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    firebaseUI(LocalContext.current)
                }
            }
        }
    }
}

@Composable
fun firebaseUI(context: Context) {

    // on below line creating
    // variable for message.
    val message = remember {
        mutableStateOf("")
    }
    // on below line creating variable for firebase
    // database and database reference.
    val firebaseDatabase = FirebaseDatabase.getInstance();
    val databaseReference = firebaseDatabase.getReference("Products");

    // on below line adding value event listener for database reference.
    databaseReference.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            // this method is call to get the realtime
            // updates in the data.
            // this method is called when the data is
            // changed in our Firebase console.
            // below line is for getting the data from
            // snapshot of our database.
            val value = snapshot.getValue(String::class.java)

            // after getting the value we are setting
            // our value to message.
            message.value = value!!
        }

        override fun onCancelled(error: DatabaseError) {
            // calling on cancelled method when we receive
            // any error or we are not able to get the data.
            Toast.makeText(context, "Fail to get data.", Toast.LENGTH_SHORT).show()
        }
    })

    // on below line creating a column
    // to display our retrieved image view.
    Column(
        // adding modifier for our column
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
           // .background(Color.White),
        // on below line adding vertical and
        // horizontal alignment for column.
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // on below line we are creating an image
        Image(
            // on below line we are adding the image url
            // from which we will  be loading our image.
            painter = rememberAsyncImagePainter(message.value),

            // on below line we are adding content
            // description for our image.
            contentDescription = "gfg image",

            // on below line we are adding modifier for our
            // image as wrap content for height and width.
            modifier = Modifier
                .wrapContentSize()
                .wrapContentHeight()
                .wrapContentWidth()
        )
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FireBaseTestTheme {
        firebaseUI(LocalContext.current)
    }
}