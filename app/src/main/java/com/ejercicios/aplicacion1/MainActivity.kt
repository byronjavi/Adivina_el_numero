package com.ejercicios.aplicacion1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var randomNumber = 0
    private var attemptsLeft = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val etInput = findViewById<EditText>(R.id.etInput)
        val btnGuess = findViewById<Button>(R.id.btnGuess)
        val tvFeedback = findViewById<TextView>(R.id.tvFeedback)
        val tvAttempts = findViewById<TextView>(R.id.tvAttempts)
        val btnReset = findViewById<Button>(R.id.btnReset)

        // Inicia el juego
        startNewGame()

        btnGuess.setOnClickListener {
            val input = etInput.text.toString()

            if (input.isEmpty()) {
                Toast.makeText(this, "Por favor ingresa un número", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val guess = input.toInt()
            if (guess < 1 || guess > 100) {
                Toast.makeText(this, "El número debe estar entre 1 y 100", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            attemptsLeft--
            tvAttempts.text = "Intentos restantes: $attemptsLeft"

            when {
                guess == randomNumber -> {
                    tvFeedback.text = "¡Correcto! Has adivinado el número."
                    endGame()
                }
                guess < randomNumber -> tvFeedback.text = "Demasiado bajo. Intenta con un número más alto."
                else -> tvFeedback.text = "Demasiado alto. Intenta con un número más bajo."
            }

            if (attemptsLeft == 0) {
                tvFeedback.text = "Has perdido. El número era $randomNumber."
                endGame()
            }

            etInput.text.clear()
        }

        btnReset.setOnClickListener {
            startNewGame()
        }
    }

    private fun startNewGame() {
        randomNumber = Random.nextInt(1, 101)
        attemptsLeft = 10
        findViewById<TextView>(R.id.tvFeedback).text = "Introduce un número para comenzar"
        findViewById<TextView>(R.id.tvAttempts).text = "Intentos restantes: $attemptsLeft"
        findViewById<Button>(R.id.btnReset).visibility = Button.GONE
        findViewById<Button>(R.id.btnGuess).isEnabled = true
    }

    private fun endGame() {
        findViewById<Button>(R.id.btnReset).visibility = Button.VISIBLE
        findViewById<Button>(R.id.btnGuess).isEnabled = false
    }
}