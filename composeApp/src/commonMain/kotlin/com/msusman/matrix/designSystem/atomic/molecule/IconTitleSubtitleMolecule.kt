package com.msusman.matrix.designSystem.atomic.molecule

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.msusman.matrix.designSystem.components.BigIcon
import com.msusman.matrix.utils.TestTags

/**
 * IconTitleSubtitleMolecule is a molecule which displays an icon, a title and a subtitle.
 *
 * @param title the title to display
 * @param subTitle the subtitle to display
 * @param iconStyle the style of the [BigIcon] to display
 * @param modifier the modifier to apply to this layout
 */
@Composable
fun IconTitleSubtitleMolecule(
    title: String,
    subTitle: String?,
    iconStyle: BigIcon.Style,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        BigIcon(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = iconStyle,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = title,

            modifier = Modifier
                .fillMaxWidth().testTag(TestTags.Login.title),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
        )
        if (subTitle != null) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = subTitle,
                modifier = Modifier.fillMaxWidth().testTag(TestTags.Login.subTitle),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
            )
        }
    }
}
