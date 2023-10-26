package com.example.lab.viewmodel

import com.example.core.error.DatabaseErrorCode
import com.example.core.error.UIRecipeErrorCode
import com.example.core.state.State
import com.example.data.database.model.entity.DbStep
import com.example.domain.recipe.model.RecipeStep
import com.example.lab.viewmodel.event.AddRecipeUIEvent
import com.example.sharedtest.core.kotest.KoinMainDispatcherSpec
import com.example.sharedtest.domain.recipe.model.RecipeStepMocks
import io.kotest.matchers.shouldBe
import org.koin.test.KoinTest
import org.koin.test.get

class AddRecipeScreenViewModelTest: KoinTest, KoinMainDispatcherSpec() {

    private lateinit var viewModel: AddRecipeScreenViewModel
    private val emptyState = AddRecipeState.empty()
    private val defaultState = AddRecipeState.default()

    private fun setupViewModel(initialState: AddRecipeState = AddRecipeState.default()) {
        viewModel = AddRecipeScreenViewModel(
            insertRecipeUseCase = get(),
            navigator = get(),
            initialState = initialState
        )
    }

    init {
        "postEvent" - {
            "UpdateRecipeTitle" - {
                "it updates the recipe title with the passed value" {
                    setupViewModel(initialState = emptyState.copy(recipeTitle = "some-initial-title"))

                    viewModel.postEvent(AddRecipeUIEvent.UpdateRecipeTitle(newTitle = "some-new-title"))

                    viewModel.state.value.recipeTitle shouldBe "some-new-title"
                }
            }
            "MarkStepForEdit" - {
                "if a step exists for the passed index then the currentStep is updated to that at the index" {
                    setupViewModel(initialState = emptyState.copy(steps = listOf(RecipeStepMocks.recipeStep, RecipeStepMocks.otherRecipeStep)))

                    viewModel.postEvent(AddRecipeUIEvent.MarkStepForEdit(stepIndex = 1))

                    viewModel.state.value.currentStep shouldBe RecipeStepMocks.otherRecipeStep
                }
                "otherwise it leaves the current step unchanged and publishes an EDIT_STEP error..." - {
                    "passed index is negative" {
                        setupViewModel()

                        viewModel.postEvent(AddRecipeUIEvent.MarkStepForEdit(stepIndex = -1))

                        with(viewModel.state.value) {
                            currentStep shouldBe RecipeStep.empty()
                            uiState shouldBe State.Error(UIRecipeErrorCode.EDIT_STEP_OUT_OF_BOUNDS)
                        }
                    }
                    "passed index exceeds size of steps" {
                        setupViewModel()

                        viewModel.postEvent(AddRecipeUIEvent.MarkStepForEdit(stepIndex = 1))

                        with(viewModel.state.value) {
                            currentStep shouldBe RecipeStep.empty()
                            uiState shouldBe State.Error(UIRecipeErrorCode.EDIT_STEP_OUT_OF_BOUNDS)
                        }
                    }
                }
            }
            "UpdateStep" - {
                "if the index is out of bounds it publishes an UPDATE_STEP_NOT_FOUND error and leaves steps unchanged" - {
                    "index exceeds steps size" {
                        setupViewModel(initialState = emptyState.copy(steps = listOf(RecipeStepMocks.recipeStep)))

                        viewModel.postEvent(AddRecipeUIEvent.UpdateStep(updateAt = 1, title = "some-updated-title"))

                        with(viewModel.state.value) {
                            uiState shouldBe State.Error(UIRecipeErrorCode.UPDATE_STEP_NOT_FOUND)
                            steps shouldBe listOf(RecipeStepMocks.recipeStep)
                        }
                    }
                    "index is negative" {
                        setupViewModel(initialState = emptyState.copy(steps = listOf(RecipeStepMocks.recipeStep)))

                        viewModel.postEvent(AddRecipeUIEvent.UpdateStep(updateAt = -1, title = "some-updated-title"))

                        with(viewModel.state.value) {
                            uiState shouldBe State.Error(UIRecipeErrorCode.UPDATE_STEP_NOT_FOUND)
                            steps shouldBe listOf(RecipeStepMocks.recipeStep)
                        }
                    }
                }
                "otherwise it replaces the field specified in the step update for the step at the passed index" - {
                    setupViewModel(initialState = emptyState.copy(
                        steps = listOf(RecipeStepMocks.recipeStep, RecipeStepMocks.otherRecipeStep, RecipeStepMocks.yetAnotherRecipeStep)
                    ))

                    viewModel.postEvent(AddRecipeUIEvent.UpdateStep(updateAt = 1, body = "some-updated-body", title = "some-updated-title"))

                    viewModel.state.value.steps shouldBe listOf(
                        RecipeStepMocks.recipeStep,
                        RecipeStepMocks.otherRecipeStep.copy(body = "some-updated-body", title = "some-updated-title"),
                        RecipeStepMocks.yetAnotherRecipeStep
                    )
                }
            }
            "DeleteStep" - {
                "if no step exists at the passed index it publishes a DELETE_STEP_NOT_FOUND error and leaves steps unchanged" {
                    setupViewModel(initialState = emptyState.copy(steps = listOf(RecipeStepMocks.recipeStep)))

                    viewModel.postEvent(AddRecipeUIEvent.DeleteStep(deleteAt = 1))

                    with(viewModel.state.value) {
                        uiState shouldBe State.Error(UIRecipeErrorCode.DELETE_STEP_NOT_FOUND)
                        steps shouldBe listOf(RecipeStepMocks.recipeStep)
                    }
                }
                "otherwise the step associated with passed index is removed from the current steps" {
                    setupViewModel(initialState = emptyState.copy(
                        steps = listOf(RecipeStepMocks.recipeStep, RecipeStepMocks.otherRecipeStep, RecipeStepMocks.yetAnotherRecipeStep))
                    )

                    viewModel.postEvent(AddRecipeUIEvent.DeleteStep(deleteAt = 1))

                    viewModel.state.value.steps shouldBe listOf(RecipeStepMocks.recipeStep, RecipeStepMocks.yetAnotherRecipeStep)
                }
            }
            "InsertStep" - {
                "if insertAt is passed as null, it will add the current step to the end of the collection" {
                    setupViewModel(initialState = emptyState.copy(
                        steps = mutableListOf(RecipeStepMocks.recipeStep),
                        currentStep = RecipeStep(title = "some-title-under-test", body = "some-body-under-test")
                    ))

                    viewModel.postEvent(AddRecipeUIEvent.InsertStep(insertAt = null))

                    viewModel.state.value.steps shouldBe listOf(
                        RecipeStepMocks.recipeStep,
                        RecipeStep("some-title-under-test", "some-body-under-test")
                    )
                }
                "otherwise ..." - {
                    "if the insertAtIndex is out of bounds it publishes an INSERT_STEP_OOB error" {
                        setupViewModel(initialState = emptyState)

                        viewModel.postEvent(AddRecipeUIEvent.InsertStep(insertAt = 1))

                        with(viewModel.state.value) {
                            steps shouldBe emptyList()
                            uiState shouldBe State.Error(UIRecipeErrorCode.INSERT_STEP_OUT_OF_BOUNDS)
                        }
                    }
                    "otherwise it will insert the current step into the current collection of steps at the index associated to index passed in" - {
                        setupViewModel(initialState = emptyState.copy(
                            steps = listOf(RecipeStepMocks.recipeStep, RecipeStepMocks.otherRecipeStep),
                            currentStep = RecipeStep(title = "some-title-under-test", body = "some-body-under-test")
                        ))

                        viewModel.postEvent(AddRecipeUIEvent.InsertStep(insertAt = 1))

                        with(viewModel.state.value) {
                            steps shouldBe listOf(
                                RecipeStepMocks.recipeStep,
                                RecipeStep("some-title-under-test", "some-body-under-test"),
                                RecipeStepMocks.otherRecipeStep
                            )
                        }
                        "it will clear the current step details" {
                            viewModel.state.value.currentStep shouldBe RecipeStep.empty()
                        }
                    }
                }
            }
            "SaveRecipe" - {
                "if there is no recipe to save it publishes a SAVE_RECIPE error" - {
                    "recipe title is empty" {
                        setupViewModel(initialState = emptyState.copy(steps = listOf(RecipeStepMocks.recipeStep)))

                        viewModel.postEvent(AddRecipeUIEvent.SaveRecipe)

                        mockRecipeDao.didInvokeInsertRecipe shouldBe false
                        viewModel.state.value.uiState shouldBe State.Error(UIRecipeErrorCode.SAVE_RECIPE_NO_RECIPE)
                    }
                    "recipe has no steps" {
                        setupViewModel(initialState = emptyState.copy(recipeTitle = "some-recipe-title"))

                        viewModel.postEvent(AddRecipeUIEvent.SaveRecipe)

                        mockRecipeDao.didInvokeInsertRecipe shouldBe false
                        viewModel.state.value.uiState shouldBe State.Error(UIRecipeErrorCode.SAVE_RECIPE_NO_STEPS)
                    }
                }
                "if there are duplicate step titles it publishes a SAVE_RECIPE_DUPLICATE error" {
                    setupViewModel(initialState = emptyState.copy(recipeTitle = "some-recipe", steps = listOf(RecipeStepMocks.recipeStep, RecipeStepMocks.recipeStep)))

                    viewModel.postEvent(AddRecipeUIEvent.SaveRecipe)

                    mockRecipeDao.didInvokeInsertRecipe shouldBe false
                    viewModel.state.value.uiState shouldBe State.Error(UIRecipeErrorCode.SAVE_RECIPE_DUPLICATE_STEP)
                }
                "if there are steps only partially specified it publishes a SAVE_RECIPE_INCOMPLETE_STEP error" - {
                    "title is blank" {
                        setupViewModel(initialState = emptyState.copy(recipeTitle = "some-recipe", steps = listOf(RecipeStepMocks.recipeStep.copy(title = ""))))

                        viewModel.postEvent(AddRecipeUIEvent.SaveRecipe)

                        mockRecipeDao.didInvokeInsertRecipe shouldBe false
                        viewModel.state.value.uiState shouldBe State.Error(UIRecipeErrorCode.SAVE_RECIPE_INCOMPLETE_STEP)
                    }
                    "body is blank" {
                        setupViewModel(initialState = emptyState.copy(recipeTitle = "some-recipe", steps = listOf(RecipeStepMocks.recipeStep.copy(body = ""))))

                        viewModel.postEvent(AddRecipeUIEvent.SaveRecipe)

                        mockRecipeDao.didInvokeInsertRecipe shouldBe false
                        viewModel.state.value.uiState shouldBe State.Error(UIRecipeErrorCode.SAVE_RECIPE_INCOMPLETE_STEP)
                    }
                }
                "otherwise it will attempt to update the database, if this is unsuccessful it publishes INSERT_RECIPE_ERROR" {
                    setupViewModel(initialState = emptyState.copy(recipeTitle = "some-recipe", steps = listOf(RecipeStepMocks.recipeStep)))
                    mockRecipeDao.shouldThrowException = true

                    viewModel.postEvent(AddRecipeUIEvent.SaveRecipe)

                    mockRecipeDao.didInvokeInsertRecipe shouldBe true
                    mockRecipeDao.recipeSupplied!!.title shouldBe "some-recipe"
                    viewModel.state.value.uiState shouldBe State.Error(DatabaseErrorCode.INSERT_RECIPE_ERROR)
                }
                "otherwise, ignoring empty steps, it inserts the value into the database and resets all recipe data" {
                    // given
                    setupViewModel(initialState = emptyState.copy(
                        recipeTitle = "some-recipe", steps = listOf(RecipeStep.empty(), RecipeStepMocks.recipeStep, RecipeStepMocks.otherRecipeStep))
                    )
                    mockRecipeDao.insertRecipeResponse = 123

                    // when
                    viewModel.postEvent(AddRecipeUIEvent.SaveRecipe)

                    // then
                    mockRecipeDao.didInvokeInsertRecipe shouldBe true
                    mockRecipeDao.recipeSupplied!!.title shouldBe "some-recipe"
                    mockRecipeDao.stepsSupplied shouldBe listOf(
                        DbStep(recipeId = 123, title = RecipeStepMocks.recipeStep.title, body = RecipeStepMocks.recipeStep.body),
                        DbStep(recipeId = 123, title = RecipeStepMocks.otherRecipeStep.title, body = RecipeStepMocks.otherRecipeStep.body)
                    )
                    with(viewModel.state.value) {
                        steps shouldBe listOf(RecipeStep.empty())
                        currentStep shouldBe RecipeStep.empty()
                        recipeTitle shouldBe ""
                    }
                }
            }
        }
    }
}