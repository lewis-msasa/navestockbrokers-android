package com.nafepay.authentication.ui.registration

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nafepay.authentication.events.RegistrationEvent
import com.nafepay.authentication.viewModels.RegistrationViewModel
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.nafepay.common_ui.R


@Composable
fun Onboarding(
    viewModel: RegistrationViewModel
){

    OnBoardingUI(events = viewModel::handleRegistrationEvent)
}

@Composable
private fun OnBoardingUI(
    events: (event: RegistrationEvent) -> Unit,
){

    Surface(color = MaterialTheme.colorScheme.surface,
         modifier = Modifier
             .fillMaxHeight()
             .fillMaxWidth()

    ) {
        val pagerState = rememberPagerState( )

        Column(
            modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues())
        ) {
            if(pagerState.currentPage + 1 < pagerState.pageCount)
                Text(text = "Skip",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 14.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clickable { events(RegistrationEvent.RegistrationNavigationClicked) }
                )

            HorizontalPager(
                state = pagerState,
                count = pages.size,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)) { page ->

                PageUI(page = pages[page])
            }

            HorizontalPagerIndicator(pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                activeColor = MaterialTheme.colorScheme.onSurface
            )

            AnimatedVisibility(visible = pagerState.currentPage == (pages.size - 1) ) {
                OutlinedButton(shape = RoundedCornerShape(2.dp) ,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),onClick = {
                            events(RegistrationEvent.RegistrationNavigationClicked)
                    },
                    border = ButtonDefaults.outlinedButtonBorder(enabled = false),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary)) {
                    Text(text = "Get Started")
                }
            }

        }
    }

}



@Composable
fun PageUI(page: Page) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(page.image),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = page.title,
            fontSize = 28.sp, fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = page.description,
            textAlign = TextAlign.Center,fontSize = 14.sp)
        Spacer(modifier = Modifier.height(12.dp))

    }
}

val pages = listOf(
    Page(
        "Stock Market",
        "Watch and trade stocks on the Malawi stock exchange",
        R.drawable.stock_market
    ),
    Page(
        "Portfolio",
        "Manage your stock portfolio",
        R.drawable.briefcase
    ),
    Page(
        "News & Insights",
        "Get News and Insights about the stock market",
        R.drawable.news
    )
)

data class Page(val title: String,
                val description: String,
                @DrawableRes val image:Int)

