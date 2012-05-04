window.User = Backbone.Model.extend({});

window.UserCollection = Backbone.Collection.extend({
    model: User
    ,url: 'http://local.commons-user.com/backend/user'
});

window.UserListView = Backbone.View.extend({

    tagName:'ul',

    initialize:function () {
        this.model.bind("reset", this.render, this);
    },

    render:function (eventName) {
        _.each(this.model.models, function (user) {
            $(this.el).append(new UserListItemView({model:user}).render().el);
        }, this);

        return this;
    }


});

window.UserListItemView = Backbone.View.extend({

    tagName:"li",

    template:_.template($('#tpl-user-item').html()),

    render:function (eventName) {
        $(this.el).html(this.template(this.model.toJSON()));
        return this;
    }

});


// Router
var AppRouter = Backbone.Router.extend({

    routes:{
        "":"list"
    },

    list:function () {
        this.userList = new UserCollection();
        this.userListView = new UserListView({model:this.userList});

        each

        this.userList.fetch();
        $('#userList').html(this.userListView.render().el);
    }

});

var app = new AppRouter();
Backbone.history.start();

