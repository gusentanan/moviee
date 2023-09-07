package com.bagusmerta.utility.datasource

import com.bagusmerta.common_ui.R

enum class StudioEnum(
    val id: Int,
    val studioName: String,
    val logo: Int
) {
    MARVEL(420, "Marvel Studios", R.drawable.ic_marvel),
    LUCASFILM(1, "LucasFilm Ltd.", R.drawable.ic_lucasfilm),
    DISNEY(2, "Walt Disney Pictures", R.drawable.ic_disney),
    PIXAR(3, "Pixar", R.drawable.ic_pixar),
    PARAMOUNT(4, "Paramount", R.drawable.ic_paramount),
    COLUMBIA(5, "Columbia Pictures", R.drawable.ic_columbia),
    DREAMWORKS(7, "DreamWorks Pictures", R.drawable.ic_dreamworks),
    WARNERBROS(17, "Warner Bros, Entertainment", R.drawable.ic_warner),
    TWENTYCENTURYFOX(25, "20th Century Fox", R.drawable.ic_twentycentury),
    UNIVERSAL(33, "Universal Pictures", R.drawable.ic_universal)

}