package com.gwabs.a30daysofwellness.pages

import android.graphics.drawable.Icon
import android.widget.ImageButton
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gwabs.a30daysofwellness.R
import com.gwabs.a30daysofwellness.models.WellnesRepo
import com.gwabs.a30daysofwellness.models.WellnessData
import com.gwabs.a30daysofwellness.ui.theme.Poppins
import com.gwabs.a30daysofwellness.ui.theme._30DaysOfWellnessTheme
import com.gwabs.a30daysofwellness.ui.theme.Shapes
// AppBar/title bar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(modifier: Modifier = Modifier){
    TopAppBar(
        title = {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                )
        },
        modifier = modifier.fillMaxWidth()
    )
}

// Item to be displayed in list on the screen
@Composable
fun CardItem(
    modifier: Modifier = Modifier,
    wellness: WellnessData
){

    var expanded by remember {
        mutableStateOf(false)
    }

    val color by animateColorAsState(
        targetValue = if (expanded) MaterialTheme.colorScheme.tertiaryContainer
        else MaterialTheme.colorScheme.primaryContainer,
        label = "",
    )

    Card(
        elevation = CardDefaults
            .cardElevation(defaultElevation = 5.dp),
        shape = Shapes.large,
        modifier = modifier
    ){

        Column (
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
                .background(color = color)
        ){
            Row (
                modifier = modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            )
            {
                Text(
                    text ="Day ${wellness.day}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                ItemButton(
                    expanded = expanded,
                    onClicked = {
                        expanded = !expanded
                    }
                )

            }
            Text(
                text = stringResource(wellness.title),
                style = MaterialTheme.typography.bodyLarge
            )

            Box (
                modifier = modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(id = wellness.imageRes),
                    contentDescription =null,
                    contentScale = ContentScale.FillWidth,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )
            }

            if (expanded){
                Text(
                    text = stringResource(wellness.body),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Justify
                )
            }


        }
    }

}

@Composable
private fun ItemButton(
    expanded : Boolean,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier,
){

    IconButton(
        onClick = { onClicked() },
        modifier = modifier
    ){
        Icon(
            imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
            contentDescription = stringResource(R.string.expand_button_content_description),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun CardList(
    wellNesses: List<WellnessData>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
){

    val visibleState = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }

    // Fade in entry animation for the entire list
    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(
            animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
        ),
        exit = fadeOut(),
        modifier = modifier
    ) {
        LazyColumn(contentPadding = contentPadding) {
            itemsIndexed(wellNesses) { index, wellNesses ->
                CardItem(
                    wellness = wellNesses,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        // Animate each list item to slide in vertically
                        .animateEnterExit(
                            enter = slideInVertically(
                                animationSpec = spring(
                                    stiffness = Spring.StiffnessVeryLow,
                                    dampingRatio = Spring.DampingRatioLowBouncy
                                ),
                                initialOffsetY = { it * (index + 1) } // staggered entrance
                            )
                        )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
 fun HomePage(
    modifier: Modifier = Modifier
){

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBar()
        }
    ) {

       val wellness = WellnesRepo.wellNesess
        CardList(
            wellNesses = wellness,
            contentPadding = it,
            modifier = modifier.padding(horizontal = 16.dp)
        )
    }


}
// AppBar Preview
@Preview(showBackground = true,)
@Composable
fun AppBarPreview(){
    _30DaysOfWellnessTheme {
        AppBar()
    }
}

// CardItem Preview
@Preview(showBackground = true, )
@Composable
private fun CardItemPreview(){
    val wellness = WellnessData(
        R.string.day,
        R.string.item_title,
        R.drawable.image1,
        R.string.body
    )
    _30DaysOfWellnessTheme {
        CardItem(wellness = wellness, modifier = Modifier.fillMaxWidth())
    }
}

// CardList Preview
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CardListPreview(){
    _30DaysOfWellnessTheme {
        CardList(wellNesses = WellnesRepo.wellNesess)
    }
}

// HomePage Preview
@Preview(showBackground = true)
@Composable
private fun HomePagePreview(){
    _30DaysOfWellnessTheme {
        HomePage()
    }
}