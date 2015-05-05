#ifndef BOOST_SERIALIZATION_VERSION_HPP
#define BOOST_SERIALIZATION_VERSION_HPP

// MS compatible compilers support #pragma once
#if defined(_MSC_VER) && (_MSC_VER >= 1020)
# pragma once
#endif

/////////1/////////2/////////3/////////4/////////5/////////6/////////7/////////8
// version.hpp:

// (C) Copyright 2002 Robert Ramey - http://www.rrsd.com . 
// Use, modification and distribution is subject to the Boost Software
// License, Version 1.0. (See accompanying file LICENSE_1_0.txt or copy at
// http://www.lslboost.org/LICENSE_1_0.txt)

//  See http://www.lslboost.org for updates, documentation, and revision history.

#include <lslboost/config.hpp>
#include <lslboost/mpl/assert.hpp>
#include <lslboost/mpl/int.hpp>
#include <lslboost/mpl/eval_if.hpp>
#include <lslboost/mpl/identity.hpp>
#include <lslboost/mpl/integral_c_tag.hpp>

#include <lslboost/type_traits/is_base_and_derived.hpp>

namespace lslboost { 
namespace serialization {

struct basic_traits;

// default version number is 0. Override with higher version
// when class definition changes.
template<class T>
struct version
{
    template<class U>
    struct traits_class_version {
        typedef BOOST_DEDUCED_TYPENAME U::version type;
    };

    typedef mpl::integral_c_tag tag;
    // note: at least one compiler complained w/o the full qualification
    // on basic traits below
    typedef
        BOOST_DEDUCED_TYPENAME mpl::eval_if<
            is_base_and_derived<lslboost::serialization::basic_traits,T>,
            traits_class_version< T >,
            mpl::int_<0>
        >::type type;
    BOOST_STATIC_CONSTANT(int, value = version::type::value);
};

#ifndef BOOST_NO_INCLASS_MEMBER_INITIALIZATION
template<class T>
const int version<T>::value;
#endif

} // namespace serialization
} // namespace lslboost

/* note: at first it seemed that this would be a good place to trap
 * as an error an attempt to set a version # for a class which doesn't
 * save its class information (including version #) in the archive.
 * However, this imposes a requirement that the version be set after
 * the implemention level which would be pretty confusing.  If this
 * is to be done, do this check in the input or output operators when
 * ALL the serialization traits are available.  Included the implementation
 * here with this comment as a reminder not to do this!
 */
//#include <lslboost/serialization/level.hpp>
//#include <lslboost/mpl/equal_to.hpp>

#include <lslboost/mpl/less.hpp>
#include <lslboost/mpl/comparison.hpp>

// specify the current version number for the class
// version numbers limited to 8 bits !!!
#define BOOST_CLASS_VERSION(T, N)                                      \
namespace lslboost {                                                      \
namespace serialization {                                              \
template<>                                                             \
struct version<T >                                                     \
{                                                                      \
    typedef mpl::int_<N> type;                                         \
    typedef mpl::integral_c_tag tag;                                   \
    BOOST_STATIC_CONSTANT(int, value = version::type::value);          \
    BOOST_MPL_ASSERT((                                                 \
        lslboost::mpl::less<                                              \
            lslboost::mpl::int_<N>,                                       \
            lslboost::mpl::int_<256>                                      \
        >                                                              \
    ));                                                                \
    /*                                                                 \
    BOOST_MPL_ASSERT((                                                 \
        mpl::equal_to<                                                 \
            :implementation_level<T >,                                 \
            mpl::int_<object_class_info>                               \
        >::value                                                       \
    ));                                                                \
    */                                                                 \
};                                                                     \
}                                                                      \
}

#endif // BOOST_SERIALIZATION_VERSION_HPP
