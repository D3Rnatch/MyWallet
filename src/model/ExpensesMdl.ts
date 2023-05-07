export class ExpenseCategory {
    title:string = ""
    color:string = "white"

    constructor(_name:string, _color:string){
        this.title = _name;
        this.color = _color;
    }
}

export class ExpenseItem {
    category = {}
    amount:number = 0.0
    description:string = ""
    date:string = ""
    user:string = ""
}

export default class ExpensesModel {
    items: ExpenseItem[] = []

    constructor() {
        var cat0 = new ExpenseCategory("House", "red");
        var cat1 = new ExpenseCategory("Work", "yellow");
        var cat2 = new ExpenseCategory("Restaurants", "purple");
        var cat3 = new ExpenseCategory("Car", "green");
        this.items.push({amount: -130, description: "First row", date: "2023/12/04", category: cat0, user:"me"})
        this.items.push({amount: -1500, description: "First row", date: "2023/12/03", category: cat1, user:"me"})
        this.items.push({amount: -135, description: "First row", date: "2023/12/06", category: cat2, user:"me"})
        this.items.push({amount: -15, description: "First row", date: "2023/12/10", category: cat0, user:"me"})
        this.items.push({amount: 42, description: "Very very long text to be displayed om hy ", date: "2023/12/03", category: cat3, user:"amanda"});
    }

    getItems(){
        return this.items;
    }
}   