package com.example.lab

import com.example.core.error.UIRecipeErrorCode
import com.example.domain.core.state.State
import com.example.domain.recipe.model.RecipeStep
import com.example.domain.recipe.usecase.InsertRecipeUseCase
import com.example.lab.viewmodel.LabScreenViewModel
import com.example.sharedtest.domain.recipe.model.RecipeStepMocks
import com.example.sharedtest.domain.recipe.usecase.InsertRecipeUseCaseFactory
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class LabScreenViewModelTest: FreeSpec() {

    lateinit var viewModel: LabScreenViewModel
    lateinit var insertRecipeUseCase: InsertRecipeUseCase

    fun setupViewModel() {
        insertRecipeUseCase = InsertRecipeUseCaseFactory().createForTest()
        viewModel = LabScreenViewModel(
            insertRecipeUseCase = insertRecipeUseCase
        )
    }
    init {
        "updateRecipeTitle" - {
            "given that the current title is 'x', if the input is 'y' then the title is overwritten with 'y" {
                setupViewModel()
                viewModel._recipeTitle.value = "some-initial-title"

                viewModel.updateRecipeTitle("some-new-title")

                viewModel.recipeTitle.value shouldBe "some-new-title"
            }
        }
        "addStep" - {
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
                "if the insertAtIndex is out of bounds it publishes an INSERT_STEP error" {
                    setupViewModel()
                    viewModel._steps.value = mutableListOf()

                    viewModel.insertStep(1)

                    viewModel.steps.value shouldBe listOf()
                    viewModel.errorState.value shouldBe State.Error(UIRecipeErrorCode.INSERT_STEP_ERROR)
                }
                "otherwise it will insert the current step into the current collection of steps at the index associated to index passed in" - {
                    setupViewModel()
                    viewModel._steps.value = mutableListOf(
                        RecipeStepMocks.recipeStep,
                        RecipeStepMocks.otherRecipeStep
                    )
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

        }
        "markStepForEdit" - {
            "if there exists a step associated to the passed index then the currentStep is updated to the step with that index" {
                setupViewModel()
                viewModel._steps.value = mutableListOf(
                    RecipeStepMocks.recipeStep,
                    RecipeStepMocks.otherRecipeStep,
                )

                viewModel.markStepForEdit(1)

                viewModel.currentStep.value shouldBe viewModel._steps.value[1]
            }
            "otherwise it leaves the current step unchanged and publishes an EDIT_STEP error..." - {
                "passed index is negative" {
                    setupViewModel()
                    viewModel._steps.value = mutableListOf(
                        RecipeStepMocks.recipeStep,
                        RecipeStepMocks.otherRecipeStep,
                    )

                    viewModel.markStepForEdit(-1)

                    viewModel.currentStepIndex.value shouldBe RecipeStep.empty()
                }
                "passed index exceeds size of steps" {
                    setupViewModel()
                    viewModel._steps.value = mutableListOf(
                        RecipeStepMocks.recipeStep,
                        RecipeStepMocks.otherRecipeStep,
                    )

                    viewModel.markStepForEdit(2)

                    viewModel.currentStep.value shouldBe RecipeStep.empty()
                }
            }
        }
        "updateStep" - {
            true shouldBe false
        }
        "deleteStep" - {
            true shouldBe false
        }
        "saveRecipe" - {
            true shouldBe false
        }
    }
}