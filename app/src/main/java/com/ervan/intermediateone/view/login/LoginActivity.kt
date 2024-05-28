package com.ervan.intermediateone.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.ervan.intermediateone.databinding.ActivityLoginBinding
import com.ervan.intermediateone.view.ViewModelFactories.ViewModelFactory
import com.ervan.intermediateone.view.home_page.HomePageActivity
import com.ervan.intermediateone.view.signup.SignupActivity

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.loginBtn.setOnClickListener {
            val email = binding.emailEdt.text.toString()
            val password = binding.passwordEdt.text.toString()
            if (email.isEmpty()){
                Toast.makeText(this, "Silakan lengkapi informasi email Anda.",Toast.LENGTH_SHORT).show()
            }
            if (password.isEmpty()){
                Toast.makeText(this, "Silakan lengkapi informasi email Anda.",Toast.LENGTH_SHORT).show()
            } else{
                binding.progressBar.visibility = View.VISIBLE
                viewModel.userLogin(email, password).observe(this) {
                    if (it.error == false) {
                        AlertDialog.Builder(this).apply {
                            setTitle("Berhasil!")
                            setMessage("Anda berhasil login. Mari ceritakan kisah anda")
                            setPositiveButton("Lanjut") { _, _ ->
                                val intent = Intent(context, HomePageActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                            create()
                            show()
                        }
                    } else if (it.error == true) {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        if (it.message.contains("not found")){
                            AlertDialog.Builder(this).apply {
                                setTitle("Gagal!")
                                setMessage("Akun anda nampaknya belum terdaftar. Silahkan daftar dulu")
                                setPositiveButton("Lanjut") { _, _ ->
                                    val intent = Intent(context, SignupActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                    finish()
                                }
                                create()
                                show()
                            }
                        }

                    }
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.loginIv, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()


        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTv, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEdtLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTv, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEdtLayout, View.ALPHA, 1f).setDuration(100)
        val login = ObjectAnimator.ofFloat(binding.loginBtn, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                login
            )
            startDelay = 100
        }.start()
    }

    companion object {
        const val SAMPLE_TOKEN = "sample_token"
    }
}