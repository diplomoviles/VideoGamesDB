package com.amaurypm.videogamesdb.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.amaurypm.videogamesdb.application.VideoGamesDBApp
import com.amaurypm.videogamesdb.data.GameRepository
import com.amaurypm.videogamesdb.data.db.model.GameEntity
import com.amaurypm.videogamesdb.databinding.GameDialogBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException

class GameDialog(
    private val newGame: Boolean = true,
    private var game: GameEntity = GameEntity(
        title = "",
        genre = "",
        developer = ""
    ),
    private val updateUI: () -> Unit
): DialogFragment() {

    //Para agregar viewbinding al fragment
    private var _binding: GameDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var dialog: Dialog

    //Para poder tener acceso al botón de guardar
    private var saveButton: Button? = null

    //Para el repositorio
    private lateinit var repository: GameRepository

    //Aquí se crea y configura el diálogo
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        _binding = GameDialogBinding.inflate(requireActivity().layoutInflater)

        /*dialog = AlertDialog.Builder(requireContext()).setView(binding.root)
            .setTitle("Juego")
            .setPositiveButton("Guardar"){ _, _ ->
                //Click listener del botón guardar
                //Hacemos el insert

                binding.apply {
                    game.title = tietTitle.text.toString()
                    game.genre = tietGenre.text.toString()
                    game.developer = tietDeveloper.text.toString()
                }

                try{

                    lifecycleScope.launch {

                        val result = async {
                            repository.insertGame(game)
                        }

                        result.await()

                        Toast.makeText(
                            requireContext(),
                            "Juego guardado exitosamente",
                            Toast.LENGTH_SHORT
                        ).show()

                        updateUI()

                    }

                }catch(e: IOException){
                    //Manejamos la excepción
                    e.printStackTrace()
                    Toast.makeText(
                        requireContext(),
                        "Error al guardar el juego",
                        Toast.LENGTH_SHORT
                    ).show()
                }catch (e: Exception){
                    e.printStackTrace()
                }

            }
            .setNegativeButton("Cancelar"){ _, _ ->
                //Click listener del botón cancelar

            }
            .create()*/

        binding.apply {
            tietTitle.setText(game.title)
            tietGenre.setText(game.genre)
            tietDeveloper.setText(game.developer)
        }

        dialog = if(newGame)
            buildDialog("Guardar", "Cancelar", {
                //Guardar

            }, {
                //Cancelar

            })
        else
            buildDialog("Actualizar", "Eliminar", {
                //Actualizar

            }, {
                //Eliminar

            })


        return dialog
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //Se llama después de que se muestra el diálogo en pantalla
    override fun onStart() {
        super.onStart()

        //Instanciamos el repositorio
        repository = (requireContext().applicationContext as VideoGamesDBApp).repository

        //Referenciamos el botón "Guardar" del diálgo
        saveButton = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
        saveButton?.isEnabled = false

        binding.apply {
            setupTextWatcher(
                tietTitle,
                tietGenre,
                tietDeveloper
            )
        }

    }

    private fun validateFields(): Boolean =
        binding.tietTitle.text.toString().isNotEmpty()
                && binding.tietGenre.text.toString().isNotEmpty()
                && binding.tietDeveloper.text.toString().isNotEmpty()

    private fun setupTextWatcher(vararg textFields: TextInputEditText){
        val textWatcher = object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                saveButton?.isEnabled = validateFields()
            }
        }

        textFields.forEach { it.addTextChangedListener(textWatcher) }
    }

    private fun buildDialog(
        btn1Text: String,
        btn2Text: String,
        positiveButton: () -> Unit,
        negativeButton: () -> Unit,
    ): Dialog =
        AlertDialog.Builder(requireContext()).setView(binding.root)
            .setTitle("Juego")
            .setPositiveButton(btn1Text){ _, _ ->
                //Click para el botón positivo
                positiveButton()
            }
            .setNegativeButton(btn2Text){ _, _ ->
                //Click para el botón negativo
                negativeButton()
            }
            .create()

}