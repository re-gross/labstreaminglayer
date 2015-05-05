//  Copyright Neil Groves 2009. Use, modification and
//  distribution is subject to the Boost Software License, Version
//  1.0. (See accompanying file LICENSE_1_0.txt or copy at
//  http://www.lslboost.org/LICENSE_1_0.txt)
//
//
// For more information, see http://www.lslboost.org/libs/range/
//
#ifndef BOOST_RANGE_ALGORITHM_COUNT_IF_HPP_INCLUDED
#define BOOST_RANGE_ALGORITHM_COUNT_IF_HPP_INCLUDED

#include <lslboost/concept_check.hpp>
#include <lslboost/range/begin.hpp>
#include <lslboost/range/end.hpp>
#include <lslboost/range/concepts.hpp>
#include <lslboost/range/difference_type.hpp>
#include <algorithm>

namespace lslboost
{
    namespace range
    {

/// \brief template function count_if
///
/// range-based version of the count_if std algorithm
///
/// \pre SinglePassRange is a model of the SinglePassRangeConcept
/// \pre UnaryPredicate is a model of the UnaryPredicateConcept
template< class SinglePassRange, class UnaryPredicate >
inline BOOST_DEDUCED_TYPENAME lslboost::range_difference<SinglePassRange>::type
count_if(SinglePassRange& rng, UnaryPredicate pred)
{
    BOOST_RANGE_CONCEPT_ASSERT(( SinglePassRangeConcept<SinglePassRange> ));
    return std::count_if(lslboost::begin(rng), lslboost::end(rng), pred);
}

/// \overload
template< class SinglePassRange, class UnaryPredicate >
inline BOOST_DEDUCED_TYPENAME lslboost::range_difference<const SinglePassRange>::type
count_if(const SinglePassRange& rng, UnaryPredicate pred)
{
    BOOST_RANGE_CONCEPT_ASSERT(( SinglePassRangeConcept<const SinglePassRange> ));
    return std::count_if(lslboost::begin(rng), lslboost::end(rng), pred);
}

    } // namespace range
    using range::count_if;
} // namespace lslboost

#endif // include guard
