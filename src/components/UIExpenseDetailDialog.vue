<script setup lang="ts">
  import UIExpenseDetail from '@/components/UIExpenseDetail.vue'
  import { ExpenseItem, ExpenseCategory, ExpenseChild } from "../model/ExpensesMdl"
  const props = defineProps({
            expenseItem: {type: ExpenseItem}
            , categories: Object as PropType<ExpenseCategory[]>
            , users: Object as PropType<string[]>
    })


</script>

<template>
    <v-dialog
        v-model="dialog"
        width="auto"
    >
    <UIExpenseDetail cateog/>
    </v-dialog>
</template>
