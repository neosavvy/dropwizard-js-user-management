window.User = Backbone.Model.extend({
    defaults:{
        "id":null,
        "firstName":"",
        "lastName":"",
        "email":""
    }
});

window.UserCollection = Backbone.Collection.extend({
    model: User
    ,url: 'http://local.commons-user.com/backend/user'
});

window.UserView = Backbone.View.extend({

    template:_.template($('#tpl-user-detail').html()),

    initialize:function() {
        this.model.bind("change", this.render, this);
    },

    events:{
        "click .save":"saveUser"
    },

    saveUser:function () {
        this.model.set({
            firstName:$('#firstName').val(),
            lastName:$('#lastName').val(),
            email:$('#email').val()
        });
        if (this.model.isNew()) {
            app.userList.create(this.model);
        } else {
            this.model.save();
        }
        return false;
    },

    render:function (eventName) {
        $(this.el).html(this.template(this.model.toJSON()));
        return this;
    },

    close:function () {
        $(this.el).unbind();
        $(this.el).empty();
    }
})

window.UserHeaderView = Backbone.View.extend({

    template:_.template($('#tpl-header').html()),

    initialize: function() {
        this.render()
    },

    render: function( eventName ) {
        $(this.el).html(this.template());
        return this;
    },

    events:{
        "click .new":"newUser"
    },

    newUser:function(event) {
        if( app.userView ) app.userView.close();
        app.userView = new UserView({model: new User()});
        $('#userDetail').html(app.userView.render().el);
        return false;
    }

})

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
        "":"list",
        "users/:id":"userDetails"
    },

    initialize: function() {
        $('#userHeader').html(new UserHeaderView().render().el);
    },

    list:function () {
        this.userList = new UserCollection();
        this.userListView = new UserListView({model:this.userList});
        this.userList.fetch();
        $('#userList').html(this.userListView.render().el);
    },

    userDetails:function(id) {
        this.user = this.userList.get(id);
        if( app.userView ) app.userView.close();
        this.userView = new UserView({model:this.user});
        $('#userDetail').html(this.userView.render().el);
    }

});

var app = new AppRouter();
Backbone.history.start();

