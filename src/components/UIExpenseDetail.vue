<script lang="ts">
    import { PropType, defineComponent } from 'vue'
    import { ExpenseItem, ExpenseChild, ExpenseCategory, cloneable } from "../model/ExpensesMdl"
    import { ExpensesLogic } from "../model/ExpensesLogic"
import { logicalExpression, moduleExpression } from '@babel/types'

    enum EditStates
    {
      Create,
      Edit
    }

    let itemLogic:ExpensesLogic = new ExpensesLogic()

    export default defineComponent({

      name: "ExpenseDetailWidget",

      // type inference enabled
      props: {
        categories: Object as PropType<ExpenseCategory[]>,
        users: Object as PropType<string[]>
      },

      emits: {
        saveChanges(item: ExpenseItem, addition: boolean) { 
          return true 
        },
        deleteItem(item: ExpenseItem) { 
          return true 
        },
        cancel() { 
          return true 
        }
      },

      methods: {

        // Method to open in creation state.
        createItem(){
          let expItm = new ExpenseItem()
          itemLogic.setItem(expItm)
          let child = new ExpenseChild()
          if(this.categories)
          {
            child.category = cloneable.deepCopy(this.categories[0])
            itemLogic.addChildExpense(child)
          }

          this.expenseItem = expItm
          this.state = EditStates.Create
        },

        // Method to open in edition state.
        editItem(item:ExpenseItem){
          this.expenseItem = item
          itemLogic.setItem(this.expenseItem)
          this.state = EditStates.Edit
        },

        onSave() {
          let isCreate = (this.state === EditStates.Create)

          if(isCreate)
          {
            this.expenseItem.date = new Date().toDateString()
          }

          itemLogic.updateTotalAmount()
          let save = cloneable.deepCopy(this.expenseItem) as ExpenseItem
          this.$emit('saveChanges', save, (isCreate))
        },

        onDelete() {
          let remove = cloneable.deepCopy(this.expenseItem) as ExpenseItem
          this.$emit('deleteItem', remove)
        },

        onAddChild(){
          let child = new ExpenseChild()
          if(this.categories)
          {
            child.category = cloneable.deepCopy(this.categories[0])
            itemLogic.addChildExpense(child)
          }
        },

        onRemoveChild(index:number){
          itemLogic.removeChildExpense(index)
        },

        onCancel(){
          this.$emit('cancel')
        },
      },

      data() {
        return {
          state: EditStates.Create,
          expenseItem: new ExpenseItem()
        }
      },

      computed: {
        totalAmount() {
          let num:number = 0
          for(let item of this.expenseItem.child){
              num = (num + item.amount)
          }
          return num
        },

        removeItemButtonEnabled() {
          return (this.expenseItem.child.length > 1)
        },
        
        enableDeleteItem() {
          return (this.state === EditStates.Edit)
        }
      },

      mounted() {
        // Do nothing
      }
  })
</script>

<template>
      <v-toolbar>
            <v-btn icon @click="onCancel()">
              <v-icon>mdi-arrow-left</v-icon>
            </v-btn>
            <v-spacer></v-spacer>

            <v-btn icon @click="onSave()">
              <v-icon>mdi-content-save</v-icon>
            </v-btn>

            <v-btn icon  @click="onDelete()" :disabled="!enableDeleteItem">
              <v-icon>mdi-delete</v-icon>
            </v-btn>
      </v-toolbar>
        <v-container fluid>
          <v-row>
            <v-col align-self="auto">
                  <v-select
                    label="User"
                    v-model="expenseItem.user"
                    :items="users"
                    return-object
                    single-line
                    hint="User"
                  ></v-select>
            </v-col>
          </v-row>
          <v-row>
            <v-col align-self="auto">
              <v-text-field
                    :rules="[
                      value => !!value || 'Required.',
                      ]"
                    hide-details="auto"
                    v-model="expenseItem.description"
                    label="Description"
                  > </v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col align-self="center">
              <v-label>Total amount of the expense: {{expenseItem.totalAmount}} €</v-label>
            </v-col>
            <v-col>
              <v-spacer></v-spacer>
            </v-col>
            <v-col >
                <v-btn icon="mdi-plus" @click="onAddChild()"></v-btn>
            </v-col>
          </v-row>
        </v-container>
        <v-sheet
          border="md"
          color="#f3f3f3"
          rounded
        >
        <v-container>
          <v-row v-for="(item, index) of expenseItem.child">
            <v-col align-self="end" cols="6">
              <v-select
                  label="Category"
                  v-model="item.category"
                  :items="categories"
                  return-object
                  single-line
                >
                <template v-slot:selection="{item}">
                  <v-chip :color="item.raw.color">
                    {{item.raw.title}}
                  </v-chip>
                </template>
              </v-select>
            </v-col>
            
            <v-col align-self="auto" cols="4">
              <v-text-field
                :rules="[
                  value => !!value || 'Required.',
                ]"
                hide-details="auto"
                v-model.number="item.amount"
                prefix="€"
                label="amount"
                type="number"
              ></v-text-field>
            </v-col>
            <v-col class="d-flex align-center justify-center mb-6">
              <v-btn icon="mdi-delete" density="compact" @click="onRemoveChild(index)" :disabled="!removeItemButtonEnabled"></v-btn>
            </v-col>
          </v-row>
        </v-container>
      </v-sheet>
</template>
