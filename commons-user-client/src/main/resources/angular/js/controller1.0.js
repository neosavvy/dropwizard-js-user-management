function UserController($scope, $resource) {

    $scope.user = {
        firstName: '',
        lastName: '',
        email: ''
    };

    var User = $resource('/backend/user/:userId',
        null,
        {'remove': {method:'DELETE', params: {userId: '@id'}, isArray:false},
         'update': {method:'PUT', params: {userId: '@id'}, isArray:false}
          } );

    $scope.users = {};

    var self = $scope;
    self.loadUsers = function() {

        $scope.users = User.query();

    }

    self.saveUser = function()
    {
        if( $scope.user.id == null )
        {
            User.save( $scope.user, function(){
                $scope.loadUsers();
                $scope.newUser();
            }, function() {
                console.log('error');
            } );
        }
        else
        {
            User.update({userId: $scope.user.id}, $scope.user, function(){
                $scope.loadUsers();
            }, function() {
                console.log('error');
            } );
        }
    }

    self.editUser = function( user )
    {
        $scope.user = user;
        console.log("user.id=" + user.id);
        console.log("user=" + user);
    }

    self.deleteUser = function( )
    {
        User.delete({userId:$scope.user.id}, function() {
            $scope.loadUsers();
            $scope.user = {
                firstName: '',
                lastName: '',
                email: ''
            }
        })
    }

    self.newUser = function()
    {
        $scope.user = {
            firstName: '',
            lastName: '',
            email: ''
        }
    }

    self.loadUsers();
}