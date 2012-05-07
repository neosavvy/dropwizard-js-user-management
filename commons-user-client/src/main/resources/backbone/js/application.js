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

window.UserListView = Backbone.View.extend({

    tagName:'ul',

    initialize:function () {
        this.model.bind("reset", this.render, this);
        var self = this;
        this.model.bind("add", function (user) {
            $(self.el).append(new UserListItemView({model:user}).render().el);
        });
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

    initialize:function () {
        this.model.bind("change", this.render, this);
        this.model.bind("destroy", this.close, this);
    },

    render:function (eventName) {
        $(this.el).html(this.template(this.model.toJSON()));
        return this;
    },

    close:function () {
        $(this.el).unbind();
        $(this.el).remove();
    }



});

window.UserView = Backbone.View.extend({

    template:_.template($('#tpl-user-detail').html()),

    initialize:function() {
        this.model.bind("change", this.render, this);
    },

    render:function (eventName) {
        $(this.el).html(this.template(this.model.toJSON()));
        return this;
    },

    events:{
        "change input":"change",
        "click .save":"saveUser",
        "click .delete":"deleteUser"
    },

    change:function (event) {
        var target = event.target;
        console.log('changing ' + target.id + ' from: ' + target.defaultValue);
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

    deleteUser:function() {
        this.model.destroy({
            success:function () {
                alert('User deleted successfully');
                window.history.back();
            }
        })
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


// Router
var AppRouter = Backbone.Router.extend({

    routes:{
        "":"list",
        "user/:id":"userDetails"
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

