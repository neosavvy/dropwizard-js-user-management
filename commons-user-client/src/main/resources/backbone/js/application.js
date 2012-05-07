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

    el: $('#userList'),

    initialize:function () {
        this.model.bind("reset", this.render, this);
        this.model.bind("add", function (user) {
            $('#userList').append(new UserListItemView({model:user}).render().el);
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

    el: $('#userDetail'),

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
            var self = this;
            app.userList.create(this.model, {
                success: function () {
                    app.navigate('user/' + self.model.id, false);
                }
            });
        } else {
            this.model.save({
                success: function() {
                    console.log("user save was successful");
                }
            });
        }
        return false;
    },

    deleteUser:function() {
        this.model.destroy({
            success:function () {
                alert('User deleted successfully');
            }
        });
        return false;
    },

    close:function () {
        $(this.el).unbind();
        $(this.el).empty();
    }
})

window.UserHeaderView = Backbone.View.extend({

    el: $('#userHeader'),

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
        app.navigate("user/new",true);
        return false;
    }

})


// Router
var AppRouter = Backbone.Router.extend({

    routes:{
        "":"list",
        "user/new":"newUser",
        "user/:id":"userDetails"
    },

    list:function () {

        this.userList = new UserCollection();
        var self = this;
        this.userList.fetch({
            success:function() {
                self.userListView = new UserListView({model:self.userList});
                self.userListView.render();
                if (self.requestedId) self.userDetails(self.requestedId);
            }
        });
    },

    userDetails:function(id) {
        if( this.userList ) {
            this.user = this.userList.get(id);
            if( app.userView ) app.userView.close();
            this.userView = new UserView({model:this.user});
            this.userView.render();
        } else {
            this.requestedId = id;
            this.list();
        }
    },

    newUser: function() {
        if( app.userView ) app.userView.close();
        app.userView = new UserView({model: new User()});
        app.userView.render();

    }

});

var app = new AppRouter();
Backbone.history.start();
var header = new UserHeaderView();
