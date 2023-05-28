import { defineStore } from 'pinia'
import ExpensesModel from "@/model/ExpensesMdl"
import { ref } from 'vue'

export const useExpenseModelStore = defineStore('ExpenseModel',
  () => {
    const expenseModel = ref(new ExpensesModel())
    return {expenseModel}
  })
