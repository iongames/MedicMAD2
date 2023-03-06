package com.example.medicmad2

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.medicmad2.ui.theme.MedicMAD2Theme
import com.example.medicmad2.view.LoginActivity
import com.example.medicmad2.view.OnboardActivity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class MedicMADTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<OnboardActivity>()

    // Изображение и текста из очереди извлекается правильно (в порядке добавления в очередь).
    @Test
    fun pagerQueueTest() {
        composeTestRule.activity.setContent {
            MedicMAD2Theme {
                composeTestRule.activity.OnboardContent()
            }
        }

        composeTestRule.onNodeWithText("Анализы").assertIsDisplayed()
        composeTestRule.onNodeWithTag("pager").performTouchInput {
            swipeLeft()
        }
        composeTestRule.onNodeWithText("Уведомления").assertIsDisplayed()
        composeTestRule.onNodeWithTag("pager").performTouchInput {
            swipeLeft()
        }
        composeTestRule.onNodeWithText("Мониторинг").assertIsDisplayed()
    }

    // Корректное извлечение элементов из очереди (количество элементов в очереди уменьшается на единицу).
    @Test
    fun pagerCorrectQueueTest() {
        composeTestRule.activity.setContent {
            MedicMAD2Theme {
                composeTestRule.activity.OnboardContent()
            }
        }

        composeTestRule.onNodeWithText("Анализы").assertIsDisplayed()
        composeTestRule.onNodeWithTag("pager").performTouchInput {
            swipeLeft()
        }
        composeTestRule.onNodeWithText("Уведомления").assertIsDisplayed()
        composeTestRule.onNodeWithTag("pager").performTouchInput {
            swipeLeft()
        }
        composeTestRule.onNodeWithText("Мониторинг").assertIsDisplayed()
    }

    //В случае, когда в очереди несколько картинок, устанавливается правильная надпись на кнопке.
    @Test
    fun pagerCorrectButtonTextTest() {
        composeTestRule.activity.setContent {
            MedicMAD2Theme {
                composeTestRule.activity.OnboardContent()
            }
        }

        composeTestRule.onNodeWithText("Пропустить").assertIsDisplayed()
        composeTestRule.onNodeWithTag("pager").performTouchInput {
            swipeLeft()
        }
        composeTestRule.onNodeWithText("Пропустить").assertIsDisplayed()
        composeTestRule.onNodeWithTag("pager").performTouchInput {
            swipeLeft()
        }
        composeTestRule.onNodeWithText("Завершить").assertIsDisplayed()
        composeTestRule.onNodeWithTag("pager").performTouchInput {
            swipeRight()
        }
        composeTestRule.onNodeWithText("Пропустить").assertIsDisplayed()
    }

    // Случай, когда в очереди осталось только одно изображение, надпись на кнопке должна измениться на "Завершить".
    @Test
    fun pagerCorrectLastButtonTextTest() {
        composeTestRule.activity.setContent {
            MedicMAD2Theme {
                composeTestRule.activity.OnboardContent()
            }
        }

        composeTestRule.onNodeWithTag("pager").performTouchInput {
            swipeLeft()
        }
        composeTestRule.onNodeWithTag("pager").performTouchInput {
            swipeLeft()
        }
        composeTestRule.onNodeWithText("Завершить").assertIsDisplayed()
    }

    // Если очередь пустая и пользователь нажал на кнопку “Завершить”, происходит открытие экрана «Вход и регистрация/не заполнено» приложения. Если очередь не пустая – переход отсутствует.
    @Test
    fun pagerQueueClickTest() {
        composeTestRule.activity.setContent {
            MedicMAD2Theme {
                composeTestRule.activity.OnboardContent()
            }
        }

        composeTestRule.onNodeWithTag("pager").performTouchInput {
            swipeLeft()
        }
        composeTestRule.onNodeWithTag("pager").performTouchInput {
            swipeLeft()
        }
        composeTestRule.onNodeWithText("Завершить").performClick()
        composeTestRule.onNodeWithTag("login").assertIsDisplayed()
    }

    // Наличие вызова метода сохранения флага об успешном прохождении приветствия пользователем.

    @Test
    fun pagerSharedPrefsTest() {
        composeTestRule.activity.setContent {
            MedicMAD2Theme {
                composeTestRule.activity.OnboardContent()
            }
        }

        composeTestRule.onNodeWithTag("pager").performTouchInput {
            swipeLeft()
        }
        composeTestRule.onNodeWithTag("pager").performTouchInput {
            swipeLeft()
        }
        composeTestRule.onNodeWithText("Завершить").performClick()

        assertEquals(composeTestRule.activity.getSharedPreferences("shared", Context.MODE_PRIVATE).getBoolean("isFirstEnter", true), false)
    }
}