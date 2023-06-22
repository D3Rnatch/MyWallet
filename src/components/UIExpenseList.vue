<script lang="ts">
    import { defineComponent } from 'vue'
    import { useExpenseModelStore } from '../stores/Stores'

    export default defineComponent({

        name: "ExpenseListWidget",

        // type inference enabled
        props: {
        },

        emits: {
          editItem(index:number) {
            return true
          }
        },

        methods: {
          getColor(amount:number){
            if(amount < 0.) return "red"
            else if(amount >= 0. ) return "green"
          },
          editBtnClick(index:number){
            this.$emit('editItem', index)
          }
        },

        data() {
          return {
            model: useExpenseModelStore().expenseModel
          }
        }
    })
</script>


<template>
  <v-table
  hover
  single-select
  >
  <thead>
    <tr>
        <th class="text-left">
          Date
        </th>
        <th class="text-left">
          Description
        </th>
        <th class="text-left">
          Amount(€)
        </th>
      </tr>
  </thead>
  <tbody>
    <tr v-for="(item, index) in model.items">
      <td>{{ item.date }}</td>
      <td>{{ item.description }}</td>
      <td>      
        <v-chip :color="getColor(item.totalAmount)">
          {{ item.totalAmount }}
        </v-chip>
      </td>
      <td>
        <v-icon
          size="small"
          class="me-2"
          @click="editBtnClick(index)"
        >
        mdi-pencil
        </v-icon>
      </td>
    </tr>
  </tbody>
</v-table>
</template>
