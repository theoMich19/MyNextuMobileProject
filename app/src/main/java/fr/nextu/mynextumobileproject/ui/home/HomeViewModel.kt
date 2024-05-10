package fr.nextu.mynextumobileproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.nextu.mynextumobileproject.R

class HomeViewModel : ViewModel() {


    private val _title = MutableLiveData<String>().apply {
        value = "Bienvenue, jeune explorateur Swapien !\n"
    }
    val title: LiveData<String> = _title

    private val _text = MutableLiveData<String>().apply {
        value = "Nous sommes ravis de te voir t\'engager dans le voyage passionnant à la découverte de notre monde unique et riche. En tant que membre de la communauté Swapi, tu fais partie d\'une tradition ancestrale, vibrante de culture, d\'histoire et de technologies innovantes.\n\n" +
                "Ici, tu trouveras des ressources pour comprendre nos origines, apprendre notre langue, explorer nos coutumes, et même participer à nos festivités traditionnelles. C\'est une opportunité magnifique pour toi de te connecter avec tes racines, de partager tes propres expériences et de contribuer à notre futur collectif.\n\n" +
                "Nous encourageons ta curiosité et ton enthousiasme à grandir et à t'épanouir ici. Pose toutes les questions que tu souhaites, partage tes idées et prépare-toi à faire des découvertes étonnantes.\n\n\n" +
                "Encore une fois, bienvenue dans la communauté Swapienne ! Ensemble, explorons et célébrons la richesse de notre monde."
    }
    val text: LiveData<String> = _text
}