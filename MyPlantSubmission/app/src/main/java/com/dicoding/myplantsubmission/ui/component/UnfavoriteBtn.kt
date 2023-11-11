package com.dicoding.myplantsubmission.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.myplantsubmission.ui.theme.MyPlantSubmissionTheme

@Composable
fun UnfavoriteBtn(
    plantId: Long,
    onItemChanged: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(size = 5.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        color = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.primary,
        modifier = modifier.size(30.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = "Delete Icon",
            modifier = modifier
                .clickable {
                    onItemChanged(plantId)
                }
        )
    }
}

@Preview
@Composable
fun DeleteListPreview() {
    MyPlantSubmissionTheme {
        UnfavoriteBtn(plantId = 1, onItemChanged = { })
    }

}