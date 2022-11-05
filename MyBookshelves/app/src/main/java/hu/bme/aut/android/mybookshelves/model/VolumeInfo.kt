package hu.bme.aut.android.mybookshelves.model

data class VolumeInfo(

    val title: String?,
    val subtitle: String?,
    val authors: List<String>,
    val publishedDate: String?,
    val readingModes: ReadingModes?,
    val maturityRating: String?,
    val allowAnonLogging: Boolean?,
    val contentVersion: String?,
    val panelizationSummary: PanelizationSummary?,
    val imageLinks: ImageLinks?,
    val previewLink: String?,
    val infoLink: String?,
    val canonicalVolumeLink: String?,
)