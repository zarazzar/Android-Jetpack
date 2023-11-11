package com.dicoding.myplantsubmission.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dicoding.myplantsubmission.ui.theme.MyPlantSubmissionTheme

@Composable
fun PlantItem(
    imageUrl: String,
    name: String,
    description: String,
    modifier: Modifier = Modifier

) {
    Card(
        modifier = modifier.width(160.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),

    ) {
        Column(
            modifier = modifier
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(
                        shape = RoundedCornerShape(8.dp)
                    )

            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = name,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                    color = MaterialTheme.colorScheme.secondary
                )
            }

        }
    }


}

@Preview(showBackground = true)
@Composable
fun PlantItemPreview() {
    MyPlantSubmissionTheme {
        PlantItem(
            imageUrl = "https://o-cdn-cas.sirclocdn.com/parenting/images/sukulen6.width-800.format-webp.webp",
            name = "Tanaman Sukulen",
            description = "-"
        )
    }
}