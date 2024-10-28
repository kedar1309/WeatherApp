package com.example.weatherapp.ui

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentSecondBinding
import com.example.weatherapp.ui.util.Logutil
import com.example.weatherapp.ui.viewmodel.WeatherViewModel
import com.test.testcompose.ui.theme.TestComposeTheme
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    private lateinit var mViewModel: WeatherViewModel

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                TestComposeTheme {
                    Scaffold(
                        topBar = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp),

                                    contentAlignment = Alignment.Center
                                ) {
                                    displayWeatherData()
                                }

                        },
                        bottomBar = {

                        },
                        content = { paddingValues ->
                            val weather = mViewModel.test.observeAsState()
                            val city = if (weather?.value?.name.isNullOrEmpty()) "" else weather?.value?.name
                            val temp =  weather?.value?.main?.temp.toString()
                            val feelslike = if (weather?.value?.main?.feelsLike.toString().isNullOrEmpty()) "" else weather?.value?.main?.feelsLike.toString()
                            val pressure = if (weather?.value?.main?.pressure.toString().isNullOrEmpty()) "" else weather?.value?.main?.pressure.toString()
                            val humidity = if (weather?.value?.main?.humidity.toString().isNullOrEmpty()) "" else weather?.value?.main?.humidity.toString()
                            val windspeed = if (weather?.value?.wind?.speed.toString().isNullOrEmpty()) "" else weather?.value?.wind?.speed .toString()
                            val gust = if (weather?.value?.wind?.gust .toString().isNullOrEmpty()) "" else weather?.value?.wind?.gust?.toString()
                            val arrayList = weather?.value?.weather
                            var urlImage =""
                            var icon : String ="";
                            if (arrayList != null) {
                                for (item in arrayList){
                                    Logutil.d("${item.icon}")
                                    icon = item.icon.toString()
                                    break
                                }
                                 urlImage ="https://openweathermap.org/img/wn/$icon@2x.png"
                            }
                            var bitmap by remember { mutableStateOf<Bitmap?>(null)}

                            Glide.with(LocalContext.current).asBitmap()
                                .load(urlImage)
                                .into(object : CustomTarget<Bitmap>() {
                                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                        bitmap = resource
                                    }

                                    override fun onLoadCleared(placeholder: Drawable?) {}
                                })



                            Card(
                                modifier = Modifier.padding(10.dp).wrapContentSize().fillMaxWidth(),
                                elevation =  10.dp,
                            ) {

                                /*Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                   ,
                                    verticalAlignment = Alignment.CenterVertically) {
                                    Text("Temp : $temp F", fontSize = 24.sp,
                                        color = MaterialTheme.colorScheme.primary, )
                                }*/

                                Column(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .padding(paddingValues)

                                ) {


                                    Column(
                                        modifier = Modifier.wrapContentSize(),
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text("Temp : $temp F", fontSize = 24.sp,
                                            color = MaterialTheme.colorScheme.primary, )
                                        bitmap.let {
                                            bitmap?.asImageBitmap()?.let { it1 ->
                                                Image(
                                                    modifier = Modifier
                                                        .width(100.dp)
                                                        .height(100.dp)
                                                        ,
                                                    bitmap = it1,
                                                    contentScale = ContentScale.Crop,
                                                    contentDescription = ""
                                                )
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(16.dp))



                                    Text("Feels like : $feelslike F ", fontSize = 16.sp,
                                        color = MaterialTheme.colorScheme.primary)
                                    Text("Pressure : $pressure",fontSize = 16.sp,
                                        color = MaterialTheme.colorScheme.primary)
                                    Text("Humidity : $humidity %",fontSize = 16.sp,
                                        color = MaterialTheme.colorScheme.primary )
                                    Text("Windspeed : $windspeed m/h", fontSize = 16.sp,
                                        color = MaterialTheme.colorScheme.primary)
                                    Text("Gust : $gust", fontSize = 16.sp,
                                        color = MaterialTheme.colorScheme.primary)




                                }



                                }


                        },
                    )
                }
                
            }
        }
    }


    @Composable
    fun loadImage(url: String, @DrawableRes drawable: Int): MutableState<Bitmap?> {
        val bitmapState: MutableState<Bitmap?> = remember {
            mutableStateOf(null)
        }

        Glide.with(LocalContext.current)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmapState.value = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }

            })
        return bitmapState
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun displayWeatherData(){
        var refreshCount by remember { mutableStateOf(1) }

        var searchQuery by rememberSaveable { mutableStateOf("") }
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            SearchBar(
                query = searchQuery,
                modifier = Modifier
                    .padding(start = 12.dp, top = 2.dp, end = 12.dp, bottom = 12.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .fillMaxWidth(),
                onSearch = {
                           Log.d("COMPOSE", "searched $it")
                    searchQuery = it

                    getWeatherUpdate(searchQuery)
                },
                onQueryChange = { query ->
                    searchQuery = query
                },
                placeholder = {
                    Text(text = "Search weather")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = null
                    )
                },
                trailingIcon = {},
                content = {},
                active = true,
                onActiveChange = {}
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getWeatherUpdate(query: String?){
        if(!TextUtils.isEmpty(query.toString())){
            val arrayList = query.toString().split(",")
            when(arrayList.size){
                1->mViewModel.fetchWeatherFromServer(arrayList.get(0), "", "")
                2-> mViewModel.fetchWeatherFromServer(arrayList.get(0), arrayList.get(1), "")
                3->mViewModel.fetchWeatherFromServer(arrayList.get(0), arrayList.get(1), arrayList.get(2))
            }

        }else{
            Toast.makeText(activity, "City name field should not be empty", Toast.LENGTH_LONG).show()
        }
    }
}