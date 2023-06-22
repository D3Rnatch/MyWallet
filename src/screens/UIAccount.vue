<script lang="ts">

import { defineComponent, ref } from 'vue'

import UIExpenseList from '@/components/UIExpenseList.vue'
import UIExpenseDetail from '@/components/UIExpenseDetail.vue'
import { useExpenseModelStore } from '@/stores/Stores'
import { ExpenseCategory, ExpenseItem } from "@/model/ExpensesMdl"

// Notes regarding the use of v-if:
// Components will not be mounter using v-if until it is usefull
// but you'll be needing a watcher on mounted state of the component in order to use
// its methods or internal members.
// v-show in opposition will ensure you that component is mounted but not displayed ensuring the access
// to the refs.


enum Mode
{
    List,
    Edit
}

export default defineComponent({

    components: {
        UIExpenseDetail,
        UIExpenseList
    },

    computed: {
        disableTabs():boolean {
            return (this.state === Mode.Edit)
        },

        isEdit():boolean {
            return (this.state === Mode.Edit)
        },

        isList():boolean {
            return (this.state === Mode.List)
        }
    },

    methods: {
        onCancelEdit()
        {
            this.stateMachine(Mode.List)
        },

        itemUpdated(item:ExpenseItem, addition:boolean)
        {
            console.log("Item add " + addition)
            if(addition)
            {
                this.model.items.push(item)
            }
            else
            {
                // callback function returning the boolean value
                function callback_func(object: ExpenseItem): boolean 
                {
                    // if the object color is green, return true; otherwise, return false for a particular object element
                    if (object.id === item.id) {
                        return true;
                    }
                    return false;
                }

                // store searched object, returned from the find() method
                let searched_obj: number | undefined = this.model.items.findIndex(callback_func);

                this.model.items[searched_obj] = item
            }

            this.stateMachine(Mode.List)
        },

        onEditItem(index:number)
        {
            this.stateMachine(Mode.Edit)
            this.$refs.ExpenseDetailWidget.editItem(this.model.items[index])
        },

        onAddItem()
        {
            console.log("Test entry ")
            this.stateMachine(Mode.Edit)
            this.$refs.ExpenseDetailWidget.createItem()
        },

        itemDeleted(item:ExpenseItem)
        {
            this.model.items = this.model.items.filter(obj => item.id !== obj.id)
            this.stateMachine(Mode.List)
        },

        stateMachine(toState:Mode)
        {
            this.state = toState
        }

    },

    data() {
        return {
            state: Mode.List,
            tabs: "1",
            model: useExpenseModelStore().expenseModel,
            expenseItm: new ExpenseItem()
        }
    },

    mounted() {
        this.expenseItm = this.model.items[0]
    }
})
</script>

<template>
        <v-tabs
            v-model="tabs"
            fixed-tabs
            color="deep-purple-accent-4"
            align-tabs="center"
            :disabled="disableTabs">
                <v-tab value="1">Expense Details</v-tab>
                <v-tab value="2" :disabled="disableTabs">Expense Overview</v-tab>
                <v-tab value="3" :disabled="disableTabs">Exports</v-tab>
        </v-tabs>
        <v-window v-model="tabs">
            <v-window-item v-show="isList" value="1">
                <v-container fluid>

                <v-row><v-btn prepend-icon="mdi-plus" @click="onAddItem">Add</v-btn></v-row>
                <v-row><UIExpenseList @editItem="onEditItem" ref="ExpenseListWidget"/></v-row>
            </v-container>

            </v-window-item>
            <v-window-item v-show="isEdit" value="1">
                <UIExpenseDetail 
                    :categories="model.categories" 
                    :users="model.users" 
                    @saveChanges="itemUpdated" 
                    @deleteItem="itemDeleted" 
                    @cancel="onCancelEdit"
                    ref="ExpenseDetailWidget" id="ExpenseDetailWidget"/>
            </v-window-item>
            <v-window-item value="2" >
                <v-card>
                <v-card-title> Dashboard </v-card-title>
                </v-card>
            </v-window-item>
            <v-window-item value="3" >
                <v-card>
                <v-card-title> Exports </v-card-title>
                </v-card>
            </v-window-item>
        </v-window>
</template>