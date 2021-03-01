/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sampleproject.kotlin

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.R

val petList = listOf(
    Pet(R.drawable.cat1_icon, R.drawable.cat1, "Mike", "Japan"),
    Pet(R.drawable.cat2_icon, R.drawable.cat2, "Nyan", "China"),
    Pet(R.drawable.cat3_icon, R.drawable.cat3, "Yam", "Korea")
)

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            MainScreen(navController, petList)
        }
        composable(
            "detail/{petId}",
            arguments = listOf(navArgument("petId") { type = NavType.StringType })
        ) {
            backStackEntry ->
            DetailScreen(navController, backStackEntry.arguments?.getString("petId"))
        }
    }
}

@Composable
fun MainScreen(navController: NavController, petList: List<Pet>) {
    MaterialTheme {
        val typography = MaterialTheme.typography
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            ) {

                items(petList.size) { index ->
                    PetCard(navController, index.toString(), petList.get(index))
                    Spacer(
                        modifier = Modifier
                            .height(8.dp),
                    )
                }
            }
        }
    }
}

@Composable
fun PetCard(navController: NavController, id: String, pet: Pet) {
    val onClick: () -> Unit = {
        Log.d("@@@Main", "name=${pet.name}")
        navController.navigate("detail/$id")
    }
    Card {
        Row(
            Modifier
                .clickable(onClick = onClick),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(pet.iconId),
                contentDescription = null,
                modifier = Modifier
                    .height(64.dp),
            )
            Column {
                Text(pet.name, style = typography.h6)
                Text(pet.detail)
            }
        }
    }
}

@Composable
fun DetailScreen(navController: NavController, petId: String?) {
    MaterialTheme {
        val typography = MaterialTheme.typography
        val pet = petList.get(petId?.toInt()!!)
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Image(
                painter = painterResource(pet.photoId),
                contentDescription = null,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.height(16.dp))

            Text(pet.name, style = typography.h6)
            Text(pet.detail)
        }
    }
}

// @Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, showSystemUi = true)
// @Composable
fun DefaultPreview() {
//    MainScreen(null, petList)
}
