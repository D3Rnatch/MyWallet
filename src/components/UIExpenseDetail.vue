<script setup lang="ts">
    import { ref, PropType, watch, reactive } from 'vue'
    import { ExpenseItem, ExpenseCategory, ExpenseChild, cloneable } from "../model/ExpensesMdl"



    const props = defineProps({
            expenseItem: {type: ExpenseItem}
            , categories: Object as PropType<ExpenseCategory[]>
            , users: Object as PropType<string[]>
    })

    const emit = defineEmits<{
            (e: 'save-changes', item: ExpenseItem): void,
            (e: 'delete-item', item: ExpenseItem): void
    }>()


    console.log("Entering Setup")

    const date = new Date();

    let day = date.getDate();
    let month = date.getMonth() + 1;
    let year = date.getFullYear();
    let isoDate = year + "/" + month + "/" + day

    let expenseItem = new ExpenseItem(0, "", isoDate, props.categories[0], props.users[0], -1)
    if(props.expenseItem)
    {
      expenseItem = props.expenseItem.dupplicate()
    }

    const localItem = reactive({ item:expenseItem});
    const removeItemButtonEnabled = ref(false)
    const totalAmount = ref(localItem.item.getTotalAmount())

    removeItemButtonEnabled.value = (localItem.item.child.length > 1)

    watch(localItem, (item) => {
      localItem.item.refreshTotalAmount()
      totalAmount.value = localItem.item.getTotalAmount()
    })

    /// When binded props changes, force to update internal value
    watch(props, (item) => {
      localItem.item = props.expenseItem.dupplicate()
      removeItemButtonEnabled.value = (localItem.item.child.length > 1)
    })

    function onSave() {
      localItem.item.refreshTotalAmount()
      let save:ExpenseItem = cloneable.deepCopy(localItem.item)
      emit('save-changes', save)
    }

    function onDelete() {
      let save:ExpenseItem = cloneable.deepCopy(localItem.item)
      emit('delete-item', save)
    }

    function onAddChild(){
      let child = new ExpenseChild()
      child.category = props.categories[0]
      localItem.item.addChildExpense(child)
      removeItemButtonEnabled.value = true
    }

    function onRemoveChild(index:number){
      localItem.item.removeChildExpense(index)
      if(localItem.item.child.length === 1){
        removeItemButtonEnabled.value = false
      }
    }

</script>

<template>
  <v-card class="mx-auto" elevation="5" density="compact" min-width="500" max-width="600">
    <v-card-title>
      Expense Details
    </v-card-title>
    <v-card-item>
      <v-container fluid>
        <v-row>
          <v-col align-self="auto">
                <v-select
                  label="User"
                  v-model="localItem.item.user"
                  :items="props.users"
                  return-object
                  single-line
                  hint="User"
                ></v-select>
          </v-col>
          <v-col align-self="auto">
            <v-text-field
                  :rules="[
                    value => !!value || 'Required.',
                    ]"
                  hide-details="auto"
                  v-model="localItem.item.description"
                  label="Description"
                > </v-text-field>
          </v-col>
        </v-row>
        <v-row>
          <v-col align-self="center">
            <v-label>Total amount of the expense: {{totalAmount}} €</v-label>
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
            <v-row v-for="(item, index) of localItem.item.child">
              <v-col align-self="end" cols="6">
                <v-select
                    label="Category"
                    v-model="item.category"
                    :items="props.categories"
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
              <v-col align-self="auto">
                <v-btn icon="mdi-delete" @click="onRemoveChild(index)" :disabled="!removeItemButtonEnabled"></v-btn>
              </v-col>
            </v-row>
          </v-container>
        </v-sheet>        
    </v-card-item>
    <v-card-actions>
      <v-btn icon="mdi-content-save-all" @click="onSave()"></v-btn>
      <v-btn icon="mdi-delete" @click="onDelete()"></v-btn>
    </v-card-actions>
  </v-card>
</template>
