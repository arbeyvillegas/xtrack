package co.edu.udea.compumovil.xtrack.data

import co.edu.udea.compumovil.xtrack.R
import co.edu.udea.compumovil.xtrack.model.ExperienceModel

class DataSource {

    fun loadExperiences(): ArrayList<ExperienceModel>{
        return arrayListOf(
            ExperienceModel("Caminando en bello","2022-01-02 13:54","Bello",
                "Parque la chinca", arrayOf<Int>(R.mipmap.im_bello_01
                                                        , R.mipmap.im_bello_02
                                                        , R.mipmap.im_bello_03)),
            ExperienceModel("Paseo en la estrella","2022-01-15 11:30","La Estrella",
                "Laguna la encantada", arrayOf(R.mipmap.im_laestrella_01,
                                                    R.mipmap.im_laestrella_02,
                                                    R.mipmap.im_laestrella_03)),
            ExperienceModel("Subiendo la loma","2022-01-22","Sabaneta",
                "El asfixiadero", arrayOf(R.mipmap.im_sabaneta_01,
                                                R.mipmap.im_sabaneta_02,
                                                R.mipmap.im_sabaneta_03))
        )
    }

}