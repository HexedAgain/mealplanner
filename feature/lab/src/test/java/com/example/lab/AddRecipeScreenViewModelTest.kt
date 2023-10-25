package com.example.lab

import com.example.core.error.UIRecipeErrorCode
import com.example.core.state.State
import com.example.data.database.model.entity.DbStep
import com.example.domain.recipe.model.RecipeStep
import com.example.lab.viewmodel.AddRecipeScreenViewModel
import com.example.lab.viewmodel.event.AddRecipeUIEvent
import com.example.sharedtest.core.kotest.KoinMainDispatcherSpec
import com.example.sharedtest.domain.recipe.model.RecipeStepMocks
import io.kotest.matchers.shouldBe
import org.koin.test.KoinTest
import org.koin.test.get

class LabScreenViewModelTest: KoinTest, KoinMainDispatcherSpec() {

    private lateinit var viewModel: AddRecipeScreenViewModel

    private fun setupViewModel() {
        viewModel = get()
    }
    init {
        "updateRecipeTitle" - {
            "it updates the recipeTitle to that which is passed in" {
                setupViewModel()
                viewModel._recipeTitle.value = "some-initial-title"

                viewModel.updateRecipeTitle(AddRecipeUIEvent.UpdateRecipeTitle("some-new-title"))

                viewModel.recipeTitle.value shouldBe "some-new-title"
            }
        }
        "insertStep" - {
            "if insertAt is passed as null, it will add the current step to the end of the collection" {
                setupViewModel()
                viewModel._steps.value = mutableListOf(RecipeStepMocks.recipeStep)
                viewModel._currentStep.value = RecipeStep("some-title-under-test", "some-body-under-test")

                viewModel.insertStep()

                viewModel._steps.value shouldBe listOf(
                    RecipeStepMocks.recipeStep,
                    RecipeStep("some-title-under-test", "some-body-under-test")
                )
            }
            "otherwise ..." - {
                "if the insertAtIndex is out of bounds it publishes an INSERT_STEP_OOB error" {
                    setupViewModel()
                    viewModel._steps.value = mutableListOf()

                    viewModel.insertStep(1)

                    viewModel.steps.value shouldBe listOf()
                    viewModel.uiState.value shouldBe State.Error(UIRecipeErrorCode.INSERT_STEP_OUT_OF_BOUNDS)
                }
                "otherwise it will insert the current step into the current collection of steps at the index associated to index passed in" - {
                    setupViewModel()
                    viewModel._steps.value = mutableListOf(RecipeStepMocks.recipeStep, RecipeStepMocks.otherRecipeStep)
                    viewModel._currentStep.value = RecipeStep("some-title-under-test", "some-body-under-test")

                    viewModel.insertStep(1)

                    viewModel._steps.value shouldBe listOf(
                        RecipeStepMocks.recipeStep,
                        RecipeStep("some-title-under-test", "some-body-under-test"),
                        RecipeStepMocks.otherRecipeStep
                    )
                    "it will clear the current step details" {
                        viewModel.currentStep.value shouldBe RecipeStep.empty()
                    }
                }
            }
            "if there already exists a step with the current title it publishes an INSERT_STEP_DUPLICATE error" {
                setupViewModel()
                viewModel._steps.value = mutableListOf(RecipeStepMocks.recipeStep)
                viewModel._currentStep.value = RecipeStepMocks.recipeStep

                viewModel.insertStep(1)

                viewModel.steps.value shouldBe listOf(RecipeStepMocks.recipeStep)
                viewModel.uiState.value shouldBe State.Error(UIRecipeErrorCode.INSERT_STEP_DUPLICATE)
            }
        }
        "markStepForEdit" - {
            "if there exists a step associated to the passed index then the currentStep is updated to the step with that index" {
                setupViewModel()
                viewModel._steps.value = mutableListOf(
                    RecipeStepMocks.recipeStep,
                    RecipeStepMocks.otherRecipeStep,
                )

                viewModel.markStepForEdit(1)

                viewModel.currentStep.value shouldBe RecipeStepMocks.otherRecipeStep
            }
            "otherwise it leaves the current step unchanged and publishes an EDIT_STEP error..." - {
                "passed index is negative" {
                    setupViewModel()
                    viewModel._steps.value = mutableListOf()

                    viewModel.markStepForEdit(-1)

                    viewModel.currentStep.value shouldBe RecipeStep.empty()
                    viewModel.uiState.value shouldBe State.Error(UIRecipeErrorCode.EDIT_STEP_OUT_OF_BOUNDS)
                }
                "passed index exceeds size of steps" {
                    setupViewModel()
                    viewModel._steps.value = mutableListOf()

                    viewModel.markStepForEdit(1)

                    viewModel.currentStep.value shouldBe RecipeStep.empty()
                    viewModel.uiState.value shouldBe State.Error(UIRecipeErrorCode.EDIT_STEP_OUT_OF_BOUNDS)
                }
            }
        }
        "updateStep" - {
            "if no existing step has the current step title it publishes a UPDATE_STEP_NOT_FOUND error and leaves steps unchanged" {
                setupViewModel()
                viewModel._steps.value = mutableListOf(RecipeStepMocks.recipeStep)
                viewModel._currentStep.value = RecipeStepMocks.otherRecipeStep

                viewModel.updateStep()

                viewModel.uiState.value shouldBe State.Error(UIRecipeErrorCode.UPDATE_STEP_NOT_FOUND)
                viewModel.steps.value shouldBe mutableListOf(RecipeStepMocks.recipeStep)
            }
            "otherwise it replaces the step associated to the current step and resets current step" {
                setupViewModel()
                viewModel._steps.value = mutableListOf(RecipeStepMocks.recipeStep)
                viewModel._currentStep.value = RecipeStepMocks.recipeStep.copy(body = "some-other-body")

                viewModel.updateStep()

                viewModel.steps.value shouldBe listOf(RecipeStepMocks.recipeStep.copy(body = "some-other-body"))
            }
        }
        "deleteStep" - {
            "if no step exists at the passed index it publishes a DELETE_STEP_NOT_FOUND error and leaves steps unchanged" {
                setupViewModel()
                viewModel._steps.value = listOf(RecipeStepMocks.recipeStep)

                viewModel.deleteStep(1)

                viewModel.uiState.value shouldBe State.Error(UIRecipeErrorCode.DELETE_STEP_NOT_FOUND)
                viewModel.steps.value shouldBe listOf(RecipeStepMocks.recipeStep)
            }
            "otherwise the step associated with passed index is removed from the current steps" {
                setupViewModel()
                viewModel._steps.value = listOf(
                    RecipeStepMocks.recipeStep,
                    RecipeStepMocks.otherRecipeStep,
                    RecipeStepMocks.yetAnotherRecipeStep
                )

                viewModel.deleteStep(1)

                viewModel.steps.value shouldBe listOf(RecipeStepMocks.recipeStep, RecipeStepMocks.yetAnotherRecipeStep)
            }
        }
        "saveRecipe" - {
            "if there is no recipe to save it publishes a SAVE_RECIPE error" - {
                "recipe title is empty" {
                    setupViewModel()
                    viewModel._steps.value = listOf(RecipeStepMocks.recipeStep)

                    viewModel.saveRecipe()

                    mockRecipeDao.didInvokeInsertRecipe shouldBe false
                    viewModel.uiState.value shouldBe State.Error(UIRecipeErrorCode.SAVE_RECIPE_NO_RECIPE)
                }
                "recipe has no steps" {
                    setupViewModel()
                    viewModel._recipeTitle.value = "some-recipe-title"

                    viewModel.saveRecipe()

                    mockRecipeDao.didInvokeInsertRecipe shouldBe false
                    viewModel.uiState.value shouldBe State.Error(UIRecipeErrorCode.SAVE_RECIPE_NO_STEPS)
                }
            }
            "it will attempt to update the database, if this is unsuccessful it publishes SAVE_RECIPE error" {
                setupViewModel()
                viewModel._recipeTitle.value = "some-recipe"
                viewModel._steps.value = listOf(RecipeStepMocks.recipeStep)
                mockRecipeDao.shouldThrowException = true

                viewModel.saveRecipe()

                mockRecipeDao.didInvokeInsertRecipe shouldBe true
                mockRecipeDao.recipeSupplied!!.title shouldBe "some-recipe"
                viewModel.uiState.value shouldBe State.Error(UIRecipeErrorCode.SAVE_RECIPE_DB_ERROR)
            }
            "otherwise it inserts the value into the database and resets all recipe data" {
                setupViewModel()
                viewModel._recipeTitle.value = "some-recipe"
                viewModel._steps.value = listOf(RecipeStepMocks.recipeStep, RecipeStepMocks.otherRecipeStep)
                mockRecipeDao.insertRecipeResponse = 123

                viewModel.saveRecipe()

                mockRecipeDao.didInvokeInsertRecipe shouldBe true
                mockRecipeDao.recipeSupplied!!.title shouldBe "some-recipe"
                mockRecipeDao.stepsSupplied shouldBe listOf(
                    DbStep(recipeId = 123, title = RecipeStepMocks.recipeStep.title, body = RecipeStepMocks.recipeStep.body),
                    DbStep(recipeId = 123, title = RecipeStepMocks.otherRecipeStep.title, body = RecipeStepMocks.otherRecipeStep.body)
                )
                viewModel.uiState.value::class.java shouldBe State.Empty::class.java
                viewModel.steps.value shouldBe emptyList()
                viewModel.recipeTitle.value shouldBe ""
            }
        }
    }
}