package branimirbutkovic.ferit.toptvseries.ui.adapters

import branimirbutkovic.ferit.toptvseries.models.Serie

interface SerieInteractionListener {
    fun onShowDetails(serie : Serie)
}